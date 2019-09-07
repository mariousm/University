function [ errorMLP, tiempoMLP, errorRBF, tiempoRBF ] = main(neuronas)
% Programa principal

    [ errorMLP, tiempoMLP ] = MLP(neuronas);
    [ errorRBF, tiempoRBF ] = RBF(neuronas);
end