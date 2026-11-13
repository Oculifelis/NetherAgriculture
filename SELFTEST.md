# Dedicated-server smoke checks

Run `gradlew :neoforge:coverageTest -Pselftest --no-daemon --max-workers=4 "-Dorg.gradle.jvmargs=-Xmx4g"` under the shared heavy-job lock.

The suite starts a dedicated server with `--nogui`, uses an isolated world under `neoforge/build/coverage-run`, writes `build/coverage-result.json` in that project, and stops itself. The verification task fails on missing results or failed checks.

Checks cover live block/item registries; every recipe through its registry-aware codec and loaded recipe ID (conditional recipes must agree with their conditions); every block's placement and loaded loot table with a real drop calculation across states/tools; and every registered item's English name and local item/model/texture files. Blocks explicitly declaring no loot are counted separately and must have no item form. External model parents are outside this source asset check. This does not check rendering or gameplay balance.

Test sources compile only with `-Pselftest`. Normal builds exclude them. Do not use that property for release builds. There is no version change.

Current blocker on NeoForge 26.1.2.122: the test sources compile, but registry initialization aborts before the suite starts. `StriderMixin` adds an `EntityDataAccessor` to the vanilla Strider class, which NeoForge rejects in development runs because load order can change synchronized data IDs. The internal-heat flag needs a synchronized data attachment or another supported implementation; the test does not disable that check. No passing runtime result is claimed.
