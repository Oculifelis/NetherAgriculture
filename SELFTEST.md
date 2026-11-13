# Dedicated-server checks

Run the tasks :neoforge:build :neoforge:runGameTestServer under the shared medium budget. The separate development mod checks live registries, recipe parsing, block drops, item names and model references, plus real Strider heat and saved-effect restoration. Its classes and resources are absent from the ordinary jar.

NeoForge uses a synchronized boolean attachment instead of adding a vanilla synchronized-data ID. As before, the derived flag is transient; the saved mob effect restores it on the first server tick. Fabric retains the original synchronized-data implementation in its own source tree.

The ordinary development guard remains enabled. The older explicit -Pselftest coverage task remains available.

Attachment reference: https://docs.neoforged.net/docs/datastorage/attachments/
Loot reference: https://github.com/neoforged/NeoForge/blob/26.1.x/src/main/java/net/neoforged/neoforge/common/loot/LootModifierManager.java
The current loader discovers loot modifiers directly; the excluded old global list is not the active 26.x format. JEI now documents the default source groups.
