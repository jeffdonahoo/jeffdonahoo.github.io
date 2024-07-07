set title "Graph Title"
set xlabel "X Axis Label"
set ylabel "Y Axis Label"
set term gif
set output "2D.gif"
set data style lp
plot [.8:4.2] "2D.data" using 1:2 t "Curve Title", "2D.data" using 1:2:3:4 notitle with errorbars ps 0, "2D.data" using 1:5 t "Other Curve", "2D.data" using 1:5:6:7 notitle with errorbars ps 0
