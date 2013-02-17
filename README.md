## General description

This is a collection of code snippets I may have done for a number of different reasons:

- To share some code.
- To try something (new) and see whether it works and how, like code, tools, languages, features in them, etc.
- To keep a recipe or small fragment of code somewhere.
- To make some small exercise (usually considered to be a programming kata).
- To pass some time for entertainment and amusement.

Basically this is a collection of gists, but instead of keeping them at https://gist.github.com/jmora, since that does
not provide tagging capabilities I decided to keep them here in a single repository, this allows using directories to
keep them sorted. Versioning control is slightly worse when mixing all these things, but I hope not to make changes in
most of them once they are finished, so hopefully this won't be too messy.  If gist implemented some system for folders
or tags I'd switch back to that for the snippets from that point on.

## Index

The snippets grouped in directories by programming language and listed here in alphabetical order (note py and py3 may
be grouped for convenience). The snippets are so far:

### java/relationChecker

Snippet to check ontological relations between concepts, the main focus in this case was to test the Manchester syntax
and how could that be parsed  when having a repository in which ontologies could be added and removed, so you can see in
the jUnit tests that there are quite a few to check the short names in Manchester syntax could be properly used and also
full URIs to avoid collisions if the same short name was being used in several ontologies. I didn't go for the
intermediate option of having prefixed short names because it seems to me that it has the cons of both options and the
pros of none.

### py/jsonCall and py3/jsonCall

Snippet to call a JSON service, it was developed in the context of a project and a specific service, so it
includes some code to call that service with random data as a test.

