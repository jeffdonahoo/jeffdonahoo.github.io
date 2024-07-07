set data style lines
set surface 
set contour surface

set view 60, 30, 1, 1
set clabel '%8.2f'
set key right
set title "Graph Title"
set xlabel "X Axis Label"
set ylabel "Y Axis Label"
set zlabel "Z Axis Label"

set term gif
set output "3D.gif"
splot "3D.data" using 2:1:3 notitle
