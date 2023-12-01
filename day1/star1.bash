#!/usr/bin/env bash

cat AOC-1-input.txt | perl -ne '($d1) = m/^[^\d]*(\d)/; ($d2) = m/(\d)[^\d]*$/; $sum += "$d1$d2"; END {print "$sum\n"}'
