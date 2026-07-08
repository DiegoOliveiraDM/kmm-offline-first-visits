# KMM Offline-First Customer Visits

A small Kotlin Multiplatform proof focused on shared mobile architecture for
Android-first product teams.

![Kotlin](https://img.shields.io/badge/Kotlin-2.3.21-7F52FF?logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-SDK_37-3DDC84?logo=android&logoColor=white)
![iOS](https://img.shields.io/badge/iOS-Kotlin_Native-000000?logo=apple&logoColor=white)
![CI](https://github.com/DiegoOliveiraDM/kmm-offline-first-visits/actions/workflows/ci.yml/badge.svg)

## What it demonstrates

- Shared Kotlin domain rules for customer visits.
- Android, iOS device, iOS Simulator, and JVM targets.
- An offline-first repository boundary that saves locally before remote sync.
- `Flow`-based visit and synchronization state.
- Failure handling that keeps local data available.
- Common tests independent from UI and platform APIs.

The project intentionally does not include a UI or production database. Its
purpose is to make the shared architecture and business rules easy to inspect
and test without overstating the scope.

## Structure

```text
shared/src/commonMain
  data/
    InMemoryVisitRepository
    SyncState
    VisitRepository
  domain/
    ValidateVisitUseCase
    ValidationResult
  model/
    Visit

shared/src/commonTest
  data/InMemoryVisitRepositoryTest
  domain/ValidateVisitUseCaseTest

docs
  architecture.md
```

## Verify

Requires JDK 17 and Android SDK 37.

```bash
./gradlew jvmTest
./gradlew compileKotlinIosSimulatorArm64
./gradlew compileAndroidMain
```

## Architecture decision

For complex B2B mobile products, Kotlin Multiplatform is most useful when it
shares stable product logic while native apps retain control of UI,
performance, platform integrations, and release workflows.

This proof keeps those boundaries explicit: the shared module owns validation,
local-first behavior, sync state, and repository contracts. Platform apps can
provide their own persistence, networking, and presentation implementations.

See [Architecture](docs/architecture.md) for the boundaries, local-first
sequence, and production evolution.

## Scope

This repository is intentionally small. It is an architecture proof, not a
production application. It does not claim to include a UI, database, backend,
authentication, or conflict-resolution strategy.

## License

MIT
