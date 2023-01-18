# taboola_calculator

Project is divided into 4 essential components:
1. Tokenizer - predefines tokens and runs on line to find them one by one.
2. Validator - used by tokenizer to validate proper calculator structure. This could have been a part of the Parser but externalized for simplicity.
3. Parser - generates a parse tree for our calculator dictionary.
4. eval() - a post-order evaluation that does the calculation.

Things I would have liked to implement, but due to shortness of my/your available time I didn't yet:
* Negative values
* Lines with increments only

