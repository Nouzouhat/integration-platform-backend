# Guide de résolution - Problème de port Kafka sur Windows

## Problème rencontré

```
Error response from daemon: ports are not available: exposing port TCP 0.0.0.0:9092 -> 127.0.0.1:0:
bind: An attempt was made to access a socket in a way forbidden by its access permissions.
```

## Cause

Sur Windows, certaines plages de ports sont **réservées par le système** (notamment par Hyper-V).
Le port par défaut de Kafka (9092) se trouve dans la plage réservée **9089-9188**.

## Diagnostic

Pour vérifier les plages de ports réservés sur votre système :

```powershell
netsh interface ipv4 show excludedportrange protocol=tcp
```

Exemple de sortie :

```
Port de début    Port de fin
-------------    -----------
      9089        9188        # ⚠️ Kafka 9092 est dans cette plage !
```

## Solution appliquée

### 1. Modification du port Kafka

Le port Kafka a été changé de **9092** à **19092** (en dehors de toutes les plages réservées).

### 2. Fichiers modifiés

#### `docker-compose-kafka.yml`

```yaml
kafka:
  ports:
    - "19092:19092" # Changé de 9092 à 19092
  environment:
    KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:19092
    KAFKA_LISTENERS: PLAINTEXT://0.0.0.0:19092
```

#### `src/main/resources/application.properties`

```properties
spring.kafka.bootstrap-servers=localhost:19092
```

### 3. Redémarrage de Kafka

```bash
# Arrêter les containers existants
docker-compose -f docker-compose-kafka.yml down

# Relancer avec la nouvelle configuration
docker-compose -f docker-compose-kafka.yml up -d

# Vérifier que tout fonctionne
docker ps
docker logs kafka --tail 20
```

## Vérification

Pour vérifier que Kafka fonctionne correctement :

```bash
# Voir les containers en cours
docker ps

# Vérifier les logs de Kafka
docker logs kafka

# Tester la connexion au port
netstat -ano | findstr :19092
```

## Alternatives si le problème persiste

### Option 1 : Libérer les ports réservés (nécessite des droits admin)

```powershell
# Désactiver Hyper-V temporairement
dism.exe /Online /Disable-Feature:Microsoft-Hyper-V

# Redémarrer
# Puis réactiver si nécessaire
dism.exe /Online /Enable-Feature:Microsoft-Hyper-V
```

### Option 2 : Utiliser un autre port

Choisissez un port en dehors des plages réservées :

- **19092** (recommandé, utilisé actuellement)
- **29092**
- **39092**

### Option 3 : Réserver un port spécifique

```powershell
# Réserver le port 9092 pour votre application (admin requis)
netsh int ipv4 add excludedportrange protocol=tcp startport=9092 numberofports=1
```

## Ports utilisés par le projet

| Service    | Port  | Description                     |
| ---------- | ----- | ------------------------------- |
| Kafka      | 19092 | Broker Kafka (modifié)          |
| Zookeeper  | 2181  | Coordination Kafka              |
| Spring App | 8080  | API REST                        |
| H2 Console | 8080  | Console H2 (/h2-console)        |
| Swagger    | 8080  | Documentation API (/swagger-ui) |

## Notes importantes

- ✅ Le port **19092** est maintenant le port par défaut pour Kafka dans ce projet
- ✅ Tous les fichiers de configuration ont été mis à jour
- ✅ Le module notification fonctionne avec ce nouveau port
- ⚠️ Si vous partagez le projet, informez les autres développeurs du changement de port

## Commandes utiles

```bash
# Voir tous les containers
docker ps -a

# Arrêter tous les containers
docker-compose -f docker-compose-kafka.yml down

# Voir les logs en temps réel
docker-compose -f docker-compose-kafka.yml logs -f

# Redémarrer un container spécifique
docker restart kafka

# Nettoyer complètement
docker-compose -f docker-compose-kafka.yml down -v
docker system prune -a
```

## Résolution réussie ✅

Le problème a été résolu en changeant le port Kafka de **9092** à **19092**.
Kafka démarre maintenant correctement et est accessible sur `localhost:19092`.
