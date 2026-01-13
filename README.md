# 🎓 Plateforme de Gestion d'Intégration ESIEA

Application Spring Boot pour gérer la semaine d'intégration des étudiants ESIEA.

## 📋 Table des Matières

- [Fonctionnalités](#-fonctionnalités)
- [Architecture](#-architecture)
- [Technologies](#-technologies)
- [Démarrage Rapide](#-démarrage-rapide)
- [Authentification](#-authentification)
- [API Endpoints](#-api-endpoints)
- [Tests](#-tests)
- [Données de Test](#-données-de-test)

## ✨ Fonctionnalités

### 🔐 Authentification & Autorisation

- Inscription et connexion avec JWT (durée: 24h)
- Rôles: **ETUDIANT** et **ADMIN**
- Mots de passe hashés avec BCrypt
- Gestion de profil utilisateur

### 📅 Gestion des Événements

- CRUD complet des événements (admin uniquement)
- Publication/dépublication d'événements
- Types: SOIRÉE, ATELIER, SPORT, CONFÉRENCE
- Statuts: BROUILLON, PUBLIÉ, ANNULÉ, TERMINÉ
- Export CSV des événements

### 📝 Gestion des Inscriptions

- Inscription aux événements publiés
- Gestion automatique de la liste d'attente
- Modification et annulation d'inscriptions
- Consultation des inscriptions par étudiant

### 🔔 Événements Kafka

- Publication d'événements lors de la création/publication

## 🏗️ Architecture

Le projet suit les principes de **Clean Architecture** (Hexagonal Architecture) :

```
domain/          # Logique métier pure (aucune dépendance externe)
├── model/       # Entités métier
├── port/        # Interfaces (in = use cases, out = repositories)
├── service/     # Validators
└── exception/   # Exceptions métier

usecase/         # Implémentations des use cases

adapters/        # Adapters externes
├── rest/        # Controllers REST + DTOs
├── kafka/       # Kafka producers/consumers
└── security/    # JWT + Spring Security

infrastructure/  # Infrastructure technique
├── persistence/ # JPA entities, repositories
├── mapper/      # Mappers domaine ↔ JPA
├── config/      # Configuration Spring
└── exception/   # Exception handlers
```

## 🛠️ Technologies

- **Java 17**
- **Spring Boot 3.5.9**
  - Spring Data JPA
  - Spring Security
  - Spring Kafka
- **H2 Database** (en mémoire pour dev)
- **JWT** (io.jsonwebtoken 0.12.3)
- **Swagger/OpenAPI** (Springdoc 2.8.4)
- **Lombok**
- **Maven**

## 🚀 Démarrage Rapide

### Prérequis

- Java 17+
- Maven 3.6+
- (Optionnel) Kafka pour les événements

### 1. Cloner le projet

```bash
git clone <repository-url>
cd gestion_integration_zampasi_sifi_athoumani
```

### 2. Lancer l'application

```bash
mvn spring-boot:run
```

L'application démarre sur `http://localhost:8080`

### 3. Accéder à Swagger UI

```
http://localhost:8080/swagger-ui.html
```

### 4. Accéder à H2 Console

```
http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:integration_db
User: sa
Password: (vide)
```

## 🔐 Authentification

### 1. Créer un compte (Register)

```http
POST /auth/register
Content-Type: application/json

{
  "email": "etudiant@esiea.fr",
  "motDePasse": "Password123",
  "nom": "Nom",
  "prenom": "Prénom",
  "promotion": "2024-2025",
  "numeroEtudiant": "E2024999",
  "filiere": "Informatique"
}
```

### 2. Se connecter (Login)

```http
POST /auth/login
Content-Type: application/json

{
  "email": "etudiant@esiea.fr",
  "motDePasse": "Password123"
}
```

**Réponse :**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "user": {
    "id": 1,
    "email": "etudiant@esiea.fr",
    "nom": "Nom",
    "prenom": "Prénom",
    "role": "ETUDIANT",
    ...
  }
}
```

### 3. Utiliser le token

Pour les endpoints protégés, ajoutez le header :

```
Authorization: Bearer <votre_token>
```

## 📡 API Endpoints

### Publics (pas de token requis)

| Méthode | Endpoint           | Description                   |
| ------- | ------------------ | ----------------------------- |
| POST    | `/auth/register`   | Créer un compte               |
| POST    | `/auth/login`      | Se connecter                  |
| GET     | `/evenements`      | Lister les événements publiés |
| GET     | `/evenements/{id}` | Détails d'un événement        |

### Protégés - ETUDIANT ou ADMIN

| Méthode   | Endpoint             | Description               |
| --------- | -------------------- | ------------------------- |
| GET       | `/users/me`          | Mon profil                |
| PUT       | `/users/me`          | Modifier mon profil       |
| GET       | `/inscriptions`      | Mes inscriptions          |
| POST      | `/inscriptions`      | S'inscrire à un événement |
| GET       | `/inscriptions/{id}` | Détails d'une inscription |
| PUT/PATCH | `/inscriptions/{id}` | Modifier une inscription  |
| DELETE    | `/inscriptions/{id}` | Annuler une inscription   |

### Protégés - ADMIN uniquement

| Méthode | Endpoint             | Description            |
| ------- | -------------------- | ---------------------- |
| POST    | `/evenements`        | Créer un événement     |
| PUT     | `/evenements/{id}`   | Modifier un événement  |
| PATCH   | `/evenements/{id}`   | Modifier le statut     |
| DELETE  | `/evenements/{id}`   | Supprimer un événement |
| GET     | `/evenements/export` | Export CSV             |

## 🧪 Tests

### Lancer tous les tests

```bash
mvn test
```

### Tests unitaires uniquement

```bash
mvn test -Dtest="*Test"
```

### Tests d'intégration uniquement

```bash
mvn test -Dtest="*IntegrationTest"
```

Voir `TESTS.md` pour plus de détails.

## 📊 Données de Test

Le fichier `src/main/resources/data.sql` contient des données de test :

### Comptes de test

| Email                    | Mot de passe | Rôle     |
| ------------------------ | ------------ | -------- |
| admin@esiea.fr           | Admin123     | ADMIN    |
| alice.martin@esiea.fr    | Password123  | ETUDIANT |
| bob.dupont@esiea.fr      | Password123  | ETUDIANT |
| charlie.bernard@esiea.fr | Password123  | ETUDIANT |

### Scénario de test complet

1. **Login Admin**

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@esiea.fr","motDePasse":"Admin123"}'
```

2. **Créer un événement (Admin)**

```bash
curl -X POST http://localhost:8080/evenements \
  -H "Authorization: Bearer <admin_token>" \
  -H "Content-Type: application/json" \
  -d '{
    "titre": "Nouvel Événement",
    "description": "Description",
    "dateDebut": "2026-02-01T10:00:00",
    "dateFin": "2026-02-01T12:00:00",
    "lieu": "Salle A",
    "capaciteMax": 50,
    "typeEvenement": "ATELIER"
  }'
```

3. **Login Étudiant**

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"alice.martin@esiea.fr","motDePasse":"Password123"}'
```

4. **Lister les événements (Public)**

```bash
curl http://localhost:8080/evenements
```

5. **S'inscrire à un événement (Étudiant)**

```bash
curl -X POST http://localhost:8080/inscriptions \
  -H "Authorization: Bearer <etudiant_token>" \
  -H "Content-Type: application/json" \
  -d '{
    "etudiantId": 2,
    "evenementId": 1,
    "commentaire": "Hâte de participer !"
  }'
```

6. **Consulter ses inscriptions (Étudiant)**

```bash
curl http://localhost:8080/inscriptions?etudiantId=2 \
  -H "Authorization: Bearer <etudiant_token>"
```

## 📝 Configuration

### application.properties

```properties
# Server
server.port=8080

# H2 Database
spring.datasource.url=jdbc:h2:mem:integration_db
spring.h2.console.enabled=true

# Kafka
spring.kafka.bootstrap-servers=localhost:9092

# JWT (optionnel, valeurs par défaut)
jwt.secret=MyVerySecretKeyForJWTTokenGenerationThatIsLongEnoughForHS256Algorithm
jwt.expiration=86400000  # 24 heures
```

## 🐳 Docker

### Lancer avec Docker Compose

```bash
docker-compose up
```

### Lancer Kafka uniquement

```bash
docker-compose -f docker-compose-kafka.yml up
```

## 📚 Documentation

- **Swagger UI** : `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON** : `http://localhost:8080/v3/api-docs`
- **Tests** : Voir `TESTS.md`

## 🤝 Contribution

1. Fork le projet
2. Créer une branche (`git checkout -b feature/AmazingFeature`)
3. Commit (`git commit -m 'Add AmazingFeature'`)
4. Push (`git push origin feature/AmazingFeature`)
5. Ouvrir une Pull Request

## 📄 Licence

Ce projet est développé dans le cadre académique ESIEA.

---

**Développé avec ❤️ pour ESIEA**
