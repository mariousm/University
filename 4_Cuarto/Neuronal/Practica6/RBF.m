function [ error, tiempo ] = RBF(neuronas)
    tiempo = cputime;
    x = -6:.01:6;
    y = funcion(x);
    figure;
    plot(x,y,'k+'); grid;
    xlabel('tiempo (s)');
    ylabel('salida');
    title('((-x).*(x+3).*(x-2))+(15*cos(5*x))');

    net = newrb(x,y,1e-15,1,neuronas);
    
    a = -5.995:.01:6;
    b = funcion(a);
    c = sim(net,a);
    plot(a,b,'k+');
    hold on;
    plot(a,c,'bs');
    plot(a,b-c,'r');
    grid;
    
    error = mse(net,b,c);
    tiempo = cputime - tiempo;
end