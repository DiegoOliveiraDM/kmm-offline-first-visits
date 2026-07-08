# Architecture

## Purpose

This proof isolates stable product rules in shared Kotlin while leaving
platform-specific storage, networking, UI, and lifecycle integration to the
Android and iOS applications.

## Boundaries

```text
Platform UI
    |
    v
Shared use cases and models
    |
    v
VisitRepository contract
    |
    +-- Local data source (platform implementation)
    |
    +-- Remote data source (platform implementation)
```

The included `InMemoryVisitRepository` is an executable reference
implementation. It proves the local-first sequence and synchronization state
without pretending to be a production persistence layer.

## Local-first sequence

1. Validate the visit in shared domain code.
2. Save the visit locally.
3. Expose the updated visit list immediately through `Flow`.
4. Mark the visit as pending synchronization.
5. Attempt remote synchronization.
6. Keep local data available when synchronization fails.
7. Expose `Idle`, `Syncing`, or `Failed` state to the platform UI.

## Production evolution

A production app could replace the reference repository with:

- SQLDelight or Room-backed local persistence;
- Ktor or platform networking;
- retry policies and connectivity observation;
- conflict resolution and idempotent server operations;
- platform-specific telemetry and background work.

Those concerns are intentionally excluded here so the proof stays small,
reviewable, and honest.
