#!/usr/bin/env perl

cat AOC-1-input.txt | perl -ne '$digits = {one => 1, two => 2, three => 3, four => 4, five => 5, six => 6, seven => 7, eight => 8, nine => 9, 0 => 0, 1 => 1, 2 => 2, 3 => 3, 4 => 4, 5 => 5, 6 => 6, 7 => 7, 8 => 8, 9 => 9}; ($d1) = m/^.*?(\d|one|two|three|four|five|six|seven|eight|nine)/; ($d2) = m/.*(\d|one|two|three|four|five|six|seven|eight|nine).*?$/; print "$digits->{$d1}$digits->{$d2}\t$_"; $sum += "$digits->{$d1}$digits->{$d2}"; END {print "$sum\n"}'
