($game) = m/Game (\d+)/;

@reds = m/(\d+) red/g;
@greens = m/(\d+) green/g;
@blues = m/(\d+) blue/g;

($max_red) = sort {$b <=> $a} @reds;
($max_green) = sort {$b <=> $a} @greens;
($max_blue) = sort {$b <=> $a} @blues;

$total += $max_red * $max_green * $max_blue;

END {
  print "Total - $total\n";
}
