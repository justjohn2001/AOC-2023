($game) = m/Game (\d+)/;

@reds = m/(\d+) red/g;
@greens = m/(\d+) green/g;
@blues = m/(\d+) blue/g;

($max_red) = sort {$b <=> $a} @reds;
($max_green) = sort {$b <=> $a} @greens;
($max_blue) = sort {$b <=> $a} @blues;

print "$game\t";
print "$max_red\t$max_green\t$max_blue";
print "\ttrue" if ($max_red <= 12 && $max_green <= 13 && $max_blue <= 14);
print "\n";
$raw += $game;
$total += $game if ($max_red <= 12 && $max_green <= 13 && $max_blue <= 14);

END {
  print "Raw - $raw\n";
  print "Total - $total\n";
}
