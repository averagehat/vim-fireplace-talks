from snake import *
from functools import partial

#belongs as ~/.vimrc.py
#"" hook to normal mode movements
#":autocmd CursorMoved * :call SpeakLine()
#
#"nmap <silent> <C-i> :set opfunc=NetBeansCommand<CR>g@
#

SPEAK_CHAR = False #speak letter-by-letter
SPEAK_BLOCKING = False
SETTINGS = { ":speed" : 0.5} #...
compose2 = lambda f, g: lambda x: f(g(x))
compose = partial(reduce, compose2)

def is_sentence(s): pass


speak_line = compose(speak, get_current_line)
speak_word = compose(speak, get_word)
speak_char = compose(speak, get_char)
speak_visual = compose(speak, get_visual_selection)



@opfunc("<some-motion>")
def example_opfunc(a_0, a_type):
    ''' see :help :map-operator in vim.
    @param a_type: one of "line", "block", "char"
    where block is blcokwise-visual '''
    
    if a_0: pass #visual mode
    if mode == 'v': speak_visual() #I think?
    ''' The '[ mark is positioned at the start of the text
    moved over by {motion}, the '] mark on the last character of the text.'''
    reg="a"
    with preserve_registers(reg): pass
    '''yank the register and speak it! rad.'''

def register_opfunc(key, opfunc):
    ''' 
    0. register the original python function in snake.
    1. create a vim function with a unique name, the body of which is vim_opfunc_template
    2. run that opfunc as a command
    3. register normal and visual mappings
    4. 
    '''
    vim_opfunc_template = '''function! {name}(type, ...)
    silent exe ":py " . {py_func} . a:0 . "," a:type . ")"
endfunction'''
    key_map(key, ":set opfunc={0}<CR>g@".format(vim_func_name))
    #this visual keymapping could be handled by a separate pure python function
    key_map(key, ":<C-U>call {0}(visualmode(), 1)".format(vim_func_name), mode=VISUAL_MODE) 

def speak(s): pass #return message

def get_last_motion(c1, c3): pass

last_cursor = (0, 0) 
rpartial = lambda f, arg2: lambda arg1: f(arg1, arg2)
on_event = rpartial(on_autocommand, "*")

from itertools import dropwhile
from fn import F
from operator import eq
import re
#what 
# or if reverse of "line" 
def is_sentence(buffer, offset):
    i = offset
    next_chars = dropwhile(re.compile(r'\s').match, buffer[:i:-1])
    if next(next_chars) == '.':
        return True

def is_words(selection):
    split = selection.split()
    return all(map(is_word, split))
#here is a more nomral.
#sentence.   
#foo 
#bar
#biz.

@on_event('CursorMoved') #on_events(['....
def speak_on_cursor_moved(ctx):
    ''' A word will not cover more than two lines, but a sentence can be arbitrary
    number of lines long. One options would be to look backwards (keys('(')) but meh.'''
    cursor = get_cursor()
    bufnum, lnum, col, off = get_cursor() # normal('getpos(".")')
    #dx, dy = last_cursor[0] - cursor[0], last_cursor[1] - cursor[1] 
    s = get_between(last_cursor, cursor) 
 
    #could potentiall do paragraphs but meh.
    if is_senence(s): speak_sentence()
    elif is_words(s.strip()): speak_word() #not the best because what if they moved like 5w ?
    elif "\n" in s: speak_line() # \n could be in lots of things not just lines
    #elif # speak the rest of the line? how handle $?
    else: speak_char() # keys('l') will not go past newline, so this was a character-wise motion
    last_cursor = cursor

''' This is another way to handle movement events. '''
def speak_motion(key):
    if not SPEAKING: return 
    reg = "@"
    with preserve_registers(reg) 
    '''  \" accesses the register, \y yanks '''
        keys('"{0}{1}y'.format(reg, key))
        yanked = get_register(reg)
        speak(yanked)
    '''perform the original motion'''
    keys(key)

def speak_before_key(key):
    specific_func = partial(speak_motion, key)
    key_map(key, specific_func)

# but have to handle searching

["w" ,"W" ,"e" ,"E" ,")" ,"}" ,"$" ,"<C-f>" ,"<C-d>" ,"0"]


''' This doesn't handle:
    searching
    marks
    repeat-motions
    it may not play nicely with opfunc!
    it will speak any deleted words . . . i.e. keys('dw')'''

'''
Another approach to this would be to start with a list of known movement commands. 
for each movement, map that key to do the following:
    if speech is not on, perform the key and return. else:
    yank the motion into a temporary register. 
    speak that register
    perform that actual motion.  
'''


#def register_event(user_func, events=None, file_globs=None): 
#    events = "" if not events else ','.join(events)
#    filetypes = "" if not file_globs else ','.join(file_globs) 
#    ctx = AutoCommandContext()
#    call = register_fn(partial(fn, ctx))
#    command("autocommand {events} {file_globs} :python {call}".format(**locals()))
#
#
#
