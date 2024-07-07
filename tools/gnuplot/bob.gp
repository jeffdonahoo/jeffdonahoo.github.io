set title "Graph Title"
set xlabel "X Axis Label"
set ylabel "Y Axis Label"
set term postscript eps
set output "2D.ps"
set data style lp
plot [.8:4.2] "2D.data" t "Curve Title", "2D.data" notitle with errorbars ps 0
