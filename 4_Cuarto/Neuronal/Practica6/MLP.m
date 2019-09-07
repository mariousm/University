function [ error, tiempo ] = MLP(neuronas)
    tiempo = cputime;
    x = -6:.01:6;
    y = funcion(x);
    plot(x,y,'k+'); grid;
    xlabel('tiempo (s)');
    ylabel('salida');
    title('((-x).*(x+3).*(x-2))+(15*cos(5*x))');
    net = feedforwardnet(neuronas,'trainlm');
    net = configure(net, [-6 6], [147,-214]);
    net.trainParam.lr = 0.005;
    net.trainParam.epochs = 100;
    net.trainPAram.max_fail = 45;
    net.trainParam.max_fail = 45;
    net.trainParam.goal = 1e-15;
    P=x; T=y; [net,TR] = train(net,P,T);
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