# User Guide

## Introduction

MediTask is a handy-guide for nurses to coordinate their tasks according to patients.

## Quick Start

1. Ensure that you have Java 17 or above installed.
1. Down the latest version of `Duke` from [here](http://link.to/duke).

## Features
### Adding a patient: `add`
Adds a new patient to the list of patients.
A patient with the same name cannot be added twice.
Format: `add NAME /tag TAG_NAME`

### Adding a todo task: `todo`
Adds a new item to the list of todo items.
Format: `todo TODO_NAME /tag TAG_NAME`

### Adding a deadline task: `deadline`
Adds a new item to the list of deadline items.
Format: `deadline DEADLINE_NAME /by DATE_TIME /tag TAG_NAME `

### Adding a todo task: `repeat`
Adds a new item to the list of recurring items.
Format: `repeat TODO_NAME /every RECUR_BASIS /tag TAG_NAME`

### Finding task: `find`
Finds an existing item in the list of task items.
Format: `find KEYWORD`

Example of usage: 

`todo n/Write the rest of the User Guide d/next week`

`todo n/Refactor the User Guide to remove passive voice d/13/04/2020`

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: {your answer here}

## Command Summary

{Give a 'cheat sheet' of commands here}

* Add todo `todo n/TODO_NAME d/DEADLINE`
