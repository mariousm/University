function x = valores3D()
%
%
rangos = [-4 4; -4 4; -4 4];
clusters = 4;
puntos = 100;
desvs = 0.075;
x = genclu(rangos,clusters,puntos,desvs);

figure
plot3(x(1,:),x(2,:),x(3,:),'.g')
grid on;
title('Datos de entrada');