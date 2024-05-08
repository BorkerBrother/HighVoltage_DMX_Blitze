# HighVoltage DMX Blitze Setup Guide

This guide provides step-by-step instructions on setting up HighVoltage DMX Blitze using Eclipse and QLC+ on both macOS and Windows platforms.

## Prerequisites
- Install [Eclipse IDE](https://www.eclipse.org/downloads/).

## Common Setup Steps
1. **Open Eclipse Workspace**:
   - Launch Eclipse and select or create a new workspace.
2. **Controller Connection**:
   - Ensure that the controller is properly connected to your system; it should connect automatically.

3. **Install QLC+**:
   - Download and install QLC+ from [QLC+ official website](https://www.qlcplus.org/).

## Setup on macOS
1. **Open QLC+ via Terminal**:
   - Navigate to your QLC+ application folder in the terminal.
   - Change directory to the QLC+ executable:
     ```bash
     cd /Applications/QLC+.app/Contents/MacOS
     ```
   - Start QLC+ with workspace mode enabled:
     ```bash
     ./qlcplus -w
     ```

2. **Open Project File**:
   - Open your `.qxw` project file in QLC+.

## Setup on Windows
1. **Launch QLC+**:
   - Go to the folder where QLC+ is installed.
   - Double-click on `qlcplus.exe` to start the application.

2. **Open Project File**:
   - Open your `.qxw` project file in QLC+ by navigating to `File > Open` and selecting the file.
