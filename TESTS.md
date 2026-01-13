# 🧪 Guide des Tests - Integration Platform

Ce document décrit la stratégie de test et comment exécuter les tests du projet.

## 📊 Structure des Tests

```
src/test/java/
├── com/esiea/integrationplatform/
│   ├── adapters/rest/                    # Tests d'intégration des contrôleurs
│   │   ├── InscriptionControllerIntegrationTest.java
│   │   └── EvenementControllerIntegrationTest.java
│   ├── domain/service/                   # Tests unitaires des validators
│   │   ├── InscriptionValidatorTest.java
│   │   └── EvenementValidatorTest.java
│   ├── infrastructure/persistence/       # Tests d'intégration des repositories
│   │   └── repository/
│   │       └── JpaInscriptionRepositoryTest.java
│   └── usecase/                          # Tests unitaires des use cases
│       ├── InscrireEtudiantUseCaseImplTest.java
│       ├── ModifierInscriptionUseCaseImplTest.java
│       └── AnnulerInscriptionUseCaseImplTest.java
```

## 🎯 Types de Tests

### 1️⃣ **Tests Unitaires** (Use Cases & Validators)

- **Objectif** : Tester la logique métier isolément
- **Framework** : JUnit 5 + Mockito
- **Couverture** :
  - ✅ Use cases d'inscription (créer, modifier, annuler)
  - ✅ Validators (Inscription, Événement)
- **Caractéristiques** :
  - Rapides (< 1s par test)
  - Pas de dépendances externes
  - Mocks pour les repositories

### 2️⃣ **Tests d'Intégration** (Controllers & Repositories)

- **Objectif** : Tester l'intégration des composants
- **Framework** : Spring Boot Test + MockMvc
- **Couverture** :
  - ✅ Endpoints REST (InscriptionController, EvenementController)
  - ✅ Repositories JPA (JpaInscriptionRepository)
- **Caractéristiques** :
  - Base de données H2 en mémoire
  - Contexte Spring complet
  - Tests transactionnels (rollback automatique)

## 🚀 Exécuter les Tests

### **Tous les tests**

```bash
mvn test
```

### **Tests unitaires uniquement**

```bash
mvn test -Dtest="*Test"
```

### **Tests d'intégration uniquement**

```bash
mvn test -Dtest="*IntegrationTest"
```

### **Un test spécifique**

```bash
mvn test -Dtest=InscrireEtudiantUseCaseImplTest
```

### **Avec rapport de couverture**

```bash
mvn test jacoco:report
```

## 📈 Couverture de Code

### **Use Cases**

- ✅ `InscrireEtudiantUseCaseImpl` : 6 tests

  - Inscription confirmée
  - Inscription en liste d'attente
  - Événement sans capacité max
  - Événement inexistant
  - Étudiant déjà inscrit

- ✅ `ModifierInscriptionUseCaseImpl` : 5 tests

  - Modification du statut
  - Modification du commentaire
  - Modification des deux champs
  - Inscription inexistante
  - Champs null (pas de modification)

- ✅ `AnnulerInscriptionUseCaseImpl` : 3 tests
  - Annulation réussie
  - Inscription inexistante
  - Vérification avant suppression

### **Validators**

- ✅ `InscriptionValidator` : 8 tests

  - Validation complète
  - Champs obligatoires (etudiantId, evenementId, statut, dateInscription)
  - Champs optionnels (commentaire)

- ✅ `EvenementValidator` : 10 tests
  - Validation complète
  - Champs obligatoires (titre, dates, lieu)
  - Validation des dates (fin après début)
  - Validation de la capacité (> 0)

### **Controllers**

- ✅ `InscriptionController` : 8 tests d'intégration

  - POST /inscriptions (création)
  - GET /inscriptions/{id} (récupération)
  - GET /inscriptions?etudiantId={id} (filtrage)
  - PUT /inscriptions/{id} (modification complète)
  - PATCH /inscriptions/{id} (modification partielle)
  - DELETE /inscriptions/{id} (suppression)
  - Cas d'erreur (404, 400)

- ✅ `EvenementController` : 6 tests d'intégration
  - POST /evenements (création)
  - GET /evenements (listing)
  - Validations (titre, dates)
  - Événement sans capacité max

### **Repositories**

- ✅ `JpaInscriptionRepository` : 7 tests
  - CRUD de base
  - Requêtes personnalisées (findByEtudiantId, findByEvenementId)
  - Comptage par statut
  - Vérification d'existence

## 🛠️ Technologies Utilisées

| Technologie          | Usage                        |
| -------------------- | ---------------------------- |
| **JUnit 5**          | Framework de test principal  |
| **Mockito**          | Mocking pour tests unitaires |
| **AssertJ**          | Assertions fluides           |
| **Spring Boot Test** | Tests d'intégration          |
| **MockMvc**          | Tests des contrôleurs REST   |
| **H2 Database**      | Base de données en mémoire   |
| **@DataJpaTest**     | Tests des repositories       |

## 📝 Bonnes Pratiques

### **1. Nommage des Tests**

```java
@Test
@DisplayName("Devrait créer une inscription confirmée quand l'événement a de la place")
void shouldCreateConfirmedInscriptionWhenEventHasCapacity() {
    // ...
}
```

### **2. Structure Given-When-Then**

```java
// Given - Préparer les données
Inscription inscription = createInscription();

// When - Exécuter l'action
Inscription result = useCase.execute(id);

// Then - Vérifier le résultat
assertThat(result).isNotNull();
```

### **3. Tests Isolés**

- Chaque test est indépendant
- Utilisation de `@BeforeEach` pour l'initialisation
- `@Transactional` pour le rollback automatique

### **4. Assertions Claires**

```java
assertThat(result.getStatut()).isEqualTo("CONFIRMEE");
assertThat(inscriptions).hasSize(2);
assertThatThrownBy(() -> useCase.execute(id))
    .isInstanceOf(NotFoundException.class);
```

## 🎯 Prochaines Étapes

- [ ] Ajouter des tests pour les mappers
- [ ] Ajouter des tests pour le GlobalExceptionHandler
- [ ] Configurer JaCoCo pour la couverture de code
- [ ] Ajouter des tests de performance
- [ ] Ajouter des tests end-to-end

## 📊 Statistiques

- **Total de tests** : ~45 tests
- **Tests unitaires** : ~32 tests
- **Tests d'intégration** : ~13 tests
- **Couverture estimée** : ~80% du code métier

---

**Les tests sont la garantie de la qualité du code !** ✅
