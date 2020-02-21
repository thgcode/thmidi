# ThMidi

ThMidi is a simple Android application to control MIDI synthesizers
using a hardware USB computer keyboard. Currently this is just a
proof-of-concept without many features, but feel free to contribute if
you find a feature you want is missing.

## Building

Just do `gradlew assembledebug` or `gradlew assemblerelease` to build the APK to install on your Android device.

## Credits

* [billthefarmer/mididriver: Android midi driver using Sonivox EAS library][mididriver_repo]
The synthesizer that ThMidi uses.

* [JosielSantos/android-metronome: App simples de metrônomo para Android][metronome_repo]
The project that motivated me to start with Android app development. I
used it as a base to bootstrap this project.

## License

[The MIT license][license]

[mididriver_repo]:https://github.com/billthefarmer/mididriver
[metronome_repo]:https://github.com/JosielSantos/android-metronome
[license]:https://github.com/thgcode/thmidi/blob/master/LICENSE
