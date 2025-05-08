# Changelog

## 0.1.0 - 2025-5-5

### New Features
- Added the ability to preliminarily offset terrain.  
- Added the `caloffset` command.  
- Added a line to the debug HUD to show the current position of terrain generation.  

### Improvements
- Improved some code.  

### Bug Fixes
- Fixed a crash when trying to generate chunks outside 33552992 blocks.  


## 0.1.1 - 2025-5-8

### Improvements
- Command `caloffset` has been renamed to `locate pos` as a sub command of `locate`.  
- `locate pos` now takes in a new optional argument `range` to specify the range to search for the specified position.  
- `locate pos` command can now throw more specific exceptions instead of a generic `UnexpectedException`.  
- Rewrote `appveyor.yml`.  

### Bug Fixes
- Fixed a bug where the `locate pos` command would get stuck in an infinite loop in some cases.  