# scaffold-ca-intellij-plugin

![Build](https://github.com/bancolombia/scaffold-ca-intellij-plugin/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/18314.svg)](https://plugins.jetbrains.com/plugin/18314)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/18314.svg)](https://plugins.jetbrains.com/plugin/18314)
[![Scorecards supply-chain security](https://github.com/bancolombia/scaffold-ca-intellij-plugin/actions/workflows/scorecards-analysis.yml/badge.svg)](https://github.com/bancolombia/scaffold-ca-intellij-plugin/actions/workflows/scorecards-analysis.yml)

## Template ToDo list
- [x] Create a new [IntelliJ Platform Plugin Template][template] project.
- [ ] Get familiar with the [template documentation][template].
- [x] Verify the [pluginGroup](/gradle.properties), [plugin ID](/src/main/resources/META-INF/plugin.xml) and [sources package](/src/main/kotlin).
- [x] Review the [Legal Agreements](https://plugins.jetbrains.com/docs/marketplace/legal-agreements.html).
- [x] [Publish a plugin manually](https://plugins.jetbrains.com/docs/intellij/publishing-plugin.html?from=IJPluginTemplate) for the first time.
- [x] Set the Plugin ID in the above README badges.
- [x] Set the [Deployment Token](https://plugins.jetbrains.com/docs/marketplace/plugin-upload.html).
- [ ] Click the <kbd>Watch</kbd> button on the top of the [IntelliJ Platform Plugin Template][template] to be notified about releases containing new features and fixes.

<!-- Plugin description -->
Scaffold Clean Architecture plugin provides support intellij idea to projects building with [scaffold clean architecture plugin](https://github.com/bancolombia/scaffold-clean-architecture)
<!-- Plugin description end -->

## Installation

- Using IDE built-in plugin system:
  
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "scaffold-ca-intellij-plugin"</kbd> >
  <kbd>Install Plugin</kbd>
  
- Manually:

  Download the [latest release](https://github.com/bancolombia/scaffold-ca-intellij-plugin/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>


---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
