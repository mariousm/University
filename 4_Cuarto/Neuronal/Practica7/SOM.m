function SOM(x,dimensiones,steps,vecindario,topologia,distancia)
%
%
net = selforgmap(dimensiones,steps,vecindario,topologia,distancia);
net = configure(net,x);
net.trainParam.epochs = 300;
net = train(net,x);