# Module Notification

## Vue d'ensemble

Le module Notification permet de gérer les notifications des utilisateurs de la plateforme d'intégration. Il suit l'architecture hexagonale (Clean Architecture) du projet et s'intègre avec Kafka pour la publication d'événements.

## Fonctionnalités

### 1. Notifications d'actions utilisateur

- Publie un événement Kafka à chaque action utilisateur (création, modification, suppression, etc.)
- Permet de tracer toutes les actions effectuées par les utilisateurs
- Topic Kafka : `user-action`

### 2. Notifications de changement de statut d'événement

- Notifie automatiquement tous les utilisateurs inscrits à un événement lorsque son statut change
- Crée une notification en base de données pour chaque utilisateur concerné
- Publie un événement Kafka pour le changement de statut
- Topic Kafka : `event-status-change`

## Architecture

Le module suit l'architecture hexagonale du projet :

```
notification/
├── domain/
│   ├── model/
│   │   └── Notification.java                    # Entité métier
│   ├── port/
│   │   ├── in/
│   │   │   ├── CreerNotificationUseCase.java
│   │   │   ├── ObtenirNotificationsUseCase.java
│   │   │   └── MarquerNotificationLueUseCase.java
│   │   └── out/
│   │       └── NotificationRepositoryPort.java
│   └── exception/
│       └── NotificationNotFoundException.java
├── usecase/
│   ├── CreerNotificationUseCaseImpl.java
│   ├── ObtenirNotificationsUseCaseImpl.java
│   └── MarquerNotificationLueUseCaseImpl.java
├── infrastructure/
│   ├── persistence/
│   │   ├── entity/
│   │   │   └── NotificationEntity.java          # Entité JPA
│   │   └── repository/
│   │       ├── JpaNotificationRepository.java
│   │       └── NotificationRepositoryAdapter.java
│   └── mapper/
│       └── NotificationMapper.java
└── adapters/
    ├── rest/
    │   ├── NotificationController.java
    │   └── dto/
    │       ├── CreateNotificationRequestDTO.java
    │       ├── NotificationResponseDTO.java
    │       └── NotificationDtoMapper.java
    └── kafka/
        ├── UserActionEvent.java
        ├── EventStatusChangeEvent.java
        └── NotificationKafkaListener.java
```

## API REST

### Endpoints

#### 1. Créer une notification

```http
POST /notifications
Content-Type: application/json

{
  "userId": 1,
  "type": "USER_ACTION",
  "titre": "Nouvelle action",
  "message": "Vous avez effectué une action",
  "evenementId": null,
  "action": "CREATION"
}
```

**Réponse** : `201 Created`

```json
{
  "id": 1,
  "userId": 1,
  "type": "USER_ACTION",
  "titre": "Nouvelle action",
  "message": "Vous avez effectué une action",
  "statut": "NON_LUE",
  "dateCreation": "2026-01-18T13:00:00",
  "dateLecture": null,
  "evenementId": null,
  "action": "CREATION"
}
```

#### 2. Obtenir toutes les notifications d'un utilisateur

```http
GET /notifications/user/{userId}
```

**Réponse** : `200 OK`

```json
[
  {
    "id": 1,
    "userId": 1,
    "type": "USER_ACTION",
    "titre": "Nouvelle action",
    "message": "Vous avez effectué une action",
    "statut": "NON_LUE",
    "dateCreation": "2026-01-18T13:00:00",
    "dateLecture": null,
    "evenementId": null,
    "action": "CREATION"
  }
]
```

#### 3. Obtenir les notifications non lues d'un utilisateur

```http
GET /notifications/user/{userId}/unread
```

**Réponse** : `200 OK` (même format que ci-dessus)

#### 4. Marquer une notification comme lue

```http
PATCH /notifications/{notificationId}/read?userId={userId}
```

**Réponse** : `200 OK`

```json
{
  "id": 1,
  "userId": 1,
  "type": "USER_ACTION",
  "titre": "Nouvelle action",
  "message": "Vous avez effectué une action",
  "statut": "LUE",
  "dateCreation": "2026-01-18T13:00:00",
  "dateLecture": "2026-01-18T14:00:00",
  "evenementId": null,
  "action": "CREATION"
}
```

## Événements Kafka

### 1. user-action

Publié à chaque action utilisateur (création, modification, suppression, etc.)

**Structure** :

```json
{
  "userId": 1,
  "action": "CREATION",
  "details": "Nouvel utilisateur créé : John Doe (john@example.com)",
  "timestamp": "2026-01-18T13:00:00"
}
```

### 2. event-status-change

Publié lorsque le statut d'un événement change

**Structure** :

```json
{
  "evenementId": 1,
  "titre": "Soirée d'intégration",
  "ancienStatut": "BROUILLON",
  "nouveauStatut": "PUBLIE",
  "message": "Le statut de l'événement 'Soirée d'intégration' est passé de 'BROUILLON' à 'PUBLIE'",
  "timestamp": "2026-01-18T13:00:00"
}
```

## Types de notifications

### USER_ACTION

Notifications liées aux actions utilisateur :

- CREATION : Création d'un compte
- MODIFICATION : Modification de profil
- SUPPRESSION : Suppression de compte
- CONNEXION : Connexion au système
- etc.

### EVENT_STATUS_CHANGE

Notifications de changement de statut d'événement :

- BROUILLON → PUBLIE
- PUBLIE → ANNULE
- PUBLIE → TERMINE
- etc.

## Intégration

### Publier une action utilisateur

```java
eventPublisher.publishUserAction(
    userId,
    "CREATION",
    "Nouvel utilisateur créé : John Doe"
);
```

### Publier un changement de statut d'événement

```java
eventPublisher.publishEventStatusChange(
    evenementId,
    "BROUILLON",
    "PUBLIE",
    "Soirée d'intégration"
);
```

### Créer une notification manuellement

```java
Notification notification = creerNotificationUseCase.creerNotification(
    userId,
    "USER_ACTION",
    "Titre de la notification",
    "Message de la notification",
    null,  // evenementId (optionnel)
    "CREATION"  // action (optionnel)
);
```

## Base de données

### Table notifications

| Colonne       | Type         | Description                      |
| ------------- | ------------ | -------------------------------- |
| id            | BIGINT       | Identifiant unique               |
| user_id       | BIGINT       | ID de l'utilisateur destinataire |
| type          | VARCHAR(50)  | Type de notification             |
| titre         | VARCHAR(255) | Titre de la notification         |
| message       | TEXT         | Message de la notification       |
| statut        | VARCHAR(20)  | Statut (NON_LUE, LUE)            |
| date_creation | TIMESTAMP    | Date de création                 |
| date_lecture  | TIMESTAMP    | Date de lecture (nullable)       |
| evenement_id  | BIGINT       | ID de l'événement (nullable)     |
| action        | VARCHAR(50)  | Action effectuée (nullable)      |

### Index

- `idx_notification_user` : Sur `user_id` pour optimiser les requêtes par utilisateur
- `idx_notification_statut` : Sur `statut` pour filtrer les notifications non lues
- `idx_notification_type` : Sur `type` pour filtrer par type

## Tests

Pour tester le module :

1. Créer un utilisateur → Un événement Kafka `user-action` est publié
2. Modifier le statut d'un événement → Un événement Kafka `event-status-change` est publié
3. Vérifier que les notifications sont créées automatiquement pour les utilisateurs inscrits
4. Consulter les notifications via l'API REST
5. Marquer une notification comme lue

## Configuration

Le module utilise la configuration Kafka existante du projet. Assurez-vous que Kafka est activé dans `application.properties` :

```properties
spring.kafka.enabled=true
spring.kafka.bootstrap-servers=localhost:9092
```

## Évolutions futures

- [ ] Notifications en temps réel via WebSocket
- [ ] Préférences de notification par utilisateur
- [ ] Notifications par email
- [ ] Notifications push mobile
- [ ] Agrégation des notifications similaires
- [ ] Suppression automatique des anciennes notifications
