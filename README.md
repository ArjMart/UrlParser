# UrlParser
A library to parse URI parameters in Java.

Example:
```
Template: "/path/to/resource/{STRING:itemName}/{INT:itemID}/more/paths/{BOOLEAN:itemHasBeenAddedToCart}
Path: "path/to/resource/apples/99/more/paths/false/"
Returns the following:
itemName: apples
itemID: 99
itemHasBeenAddedToCart: false
```

See doc folder (javadocs) for more usage information. Don't bother looking at the code, I haven't gotten around to documenting it well, other than the JavaDoc comments. You can also view the javadocs at arjmart.github.io/UrlParser.
