# Introduction

The package provides components to build command-line generators based on the CommonAPI framework.

# Usage

The command line tool is called `commonAPICodeGen`. The basic usage is:

```
./commonAPICodeGen -f <filename>.fdepl -o <outputdir> dbus
```

In this example, the actual generator used is the dbus generator. Run the tool without arguments to get a more detailed usage guide.


# Building

This repository depends on a number of other repositories, the build instructions include them.

Start by checking out the following repositories:

- https://github.com/Pelagicore/common-api-cmdline
- https://github.com/Pelagicore/common-api-tools
- https://github.com/Pelagicore/common-api-dbus-tools
- https://github.com/Pelagicore/common-api-dbus-integration

In order to build these repositories, start by defining and installation path. I choose to export it as follows:

```
export INSTPATH=$HOME/GENIVI/inst
```

If you want to install everything in the default location (for system wide installs), simply ignore the cmake `-D` arguments and the `INSTPATH` definition.

Then continue to build _common-api-cmdline_.

```
cd common-api-cmdline
mkdir b
cd b
    cmake -DCMAKE_INSTALL_PREFIX=$INSTPATH ..
make
make install
```
Followed by _common-api-tools_.

```
cd common-api-tools
mkdir b
cd b
cmake -DCMAKE_MODULE_PATH=$INSTPATH/lib/cmake -DCMAKE_INSTALL_PREFIX=$INSTPATH ..
make
make install
```

Next comes _common-api-dbus-integration_.

```
cd common-api-dbus-integration
mkdir b
cd b
cmake -DCMAKE_MODULE_PATH=$INSTPATH/lib/cmake -DCMAKE_INSTALL_PREFIX=$INSTPATH ..
make
make install
```

And finally _common-api-dbus-tools_.

```
cd common-api-dbus-tools
mkdir b
cd b
cmake -DCMAKE_MODULE_PATH=$INSTPATH/lib/cmake -DCMAKE_INSTALL_PREFIX=$INSTPATH ..
make
make install
```

The end result should be a toolchain installed in `INSTPATH`.

## License and Copyright

Copyright (C) 2015 Pelagicore AB

Please see `LICENSE.txt` for licensing information. This software is licensed under the MPL 2.0 license.
