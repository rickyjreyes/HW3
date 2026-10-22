# Pascal `WHEN` Statement — Compiler Coursework (HW3)

A Java-based compiler/interpreter exercise extending an instructional Pascal implementation with a proposed `WHEN`/`OTHERWISE` conditional statement. The repository contains a scanner, recursive-descent parser, intermediate-code tree, symbol table, interpreter, a placeholder compiler backend, and sample Pascal programs.

## Intended syntax

The supplied [`HW3/when.txt`](HW3/when.txt) illustrates multi-branch conditions:

```pascal
WHEN
    i = 1 => f := 10;
    i = 2 => f := 20;
    i = 3 => f := 30;
    OTHERWISE => f := -1
END;
```

This is the *intended example*, not a claim that the current implementation executes it correctly. The checked-in `WhenStatementParser` currently constructs only one expression/statement pair and an optional `OTHERWISE` statement. Additionally, `PascalSpecialSymbolToken` does not consume `=>` as a single token, although `ALSO("=>")` is declared in `PascalTokenType`. The syntax and implementation need reconciliation before the included example is usable end to end.

## Structure

- `HW3/src/Pascal.java` — command-line entry point and output listeners.
- `HW3/src/wci/frontend/` — source reading, tokenization and recursive-descent parsing.
- `HW3/src/wci/frontend/pascal/parsers/WhenStatementParser.java` — added conditional parser.
- `HW3/src/wci/intermediate/` — intermediate-code nodes and symbol tables; includes a `WHEN` node type.
- `HW3/src/wci/backend/interpreter/executors/WhenExecutor.java` — dispatches a true condition to its statement, otherwise to its optional fallback.
- `HW3/src/wci/backend/compiler/CodeGenerator.java` — instructional placeholder, not a machine-code compiler.
- `HW3/*.txt` — sample inputs and malformed examples for assignments, conditionals, loops, `CASE` and `WHEN`.

## Build and use

The project includes Eclipse metadata configured for Java 8 and can be imported into Eclipse from the nested `HW3/` directory. Alternatively, from the repository root, with a JDK installed:

```sh
mkdir -p build
javac -d build $(find HW3/src -name '*.java')
java -cp build Pascal execute -ix HW3/if.txt
```

The shell command above assumes a Unix-like shell; on Windows, import into Eclipse or compile the sources through your preferred Java tooling. Command syntax for the entry point is:

```text
Pascal execute|compile [-ix] <source-file>
```

`-i` prints the intermediate parse tree; `-x` prints a cross-reference. `compile` invokes the placeholder backend, which reports zero generated instructions. Execution and build have not been revalidated against a current JDK.

## Known limitations and provenance

This is a 2017 coursework snapshot, not a complete Pascal implementation or production compiler. The source retains instructional framework notices crediting Ronald Mak (2009); those notices should be preserved. In particular, do not use the `WHEN` example as a passing test until lexer support for `=>` and multi-branch parsing/execution are implemented and verified. No automated build or test configuration is included.
