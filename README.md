# vim-fireplace-talk 

In the spirit of related projects:
 * [phonim](https://github.com/averagehat/phonim-jython/)
 * [vimoir](https://code.google.com/p/vimoir/)


vim motions and modality make it well-suited for serving the sight-impaired.

Using: 
[phonim](https://bitbucket.org/stefika/phonemic)
[vim-fireplace](https://github.com/tpope/vim-fireplace)


## Installation

make install

## Usage

    $ cd vim-fireplace-talk
    $ make
    $ lein repl

## Options

config.clj will allow you to set volume, pitch, voice, etc. 

## Examples

...shall be forthcoming. See [phonim](https://github.com/averagehat/phonim-jython/) for now.

### Goals
Limit dependencies:
 - [ ] Seperate functionality from vim-fireplace and make it optional.
 - [ ] Seperate functionality from the REPL and lein and make it optional.

Client-side functionality:
 - [ ] Event-driven speech
 - [ ] Error speaking
 - [ ] Default key mappings
 - [ ] Inter-op speech with terminal commands/web requests (i.e. clojure-docs)
 - [ ] Speak docs
 - [ ] Speak auto-complete
 - [ ] etc.?

It is simple enough to replace lein's repl with clojure.main/repl; see Joy of Clojure exmample p. 309.
The custom repl could itself evaluate and then speak the output.

speaking autocomplete:
```
:help complete
:help ins-completion-menu
```
## License

Copyright © 2015 Michael Panciera

Distributed under the Eclipse Public License version 1.0
