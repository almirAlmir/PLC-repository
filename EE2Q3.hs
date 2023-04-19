import Control.Concurrent
import Control.Concurrent.STM
import Text.Printf

main= do
    ctrl <- newMVar True -- Impede que os clientes usem a maquina ao mesmo tempo
    pepise <- newMVar 2000
    polo   <- newMVar 2000 
    quate  <- newMVar 2000
    clientePepise <- newMVar 1
    clientePolo   <- newMVar 1
    clienteQuate  <- newMVar 1

    forkIO $ clienteUsando "Pepise-Cola" pepise clientePepise ctrl
    forkIO $ clienteUsando "Guarana Polo Norte" polo clientePolo ctrl
    forkIO $ clienteUsando "Guarana Quate" quate clienteQuate ctrl


clienteUsando ::String -> MVar Int -> MVar Int -> MVar Bool -> IO ()
clienteUsando nome refri cliente controle = loop
    where
        loop = do
            f <- takeMVar controle
            c <- takeMVar cliente
            r <- takeMVar refri
            if(r < 1000) then -- condicao para saber se é cliente ou se é reabastecimento
                do
                    printf "O refrigerante %s foi reabastecido com 1000 ml, e agora possui %d ml \n" nome (r+1000)
                    putMVar cliente c
                    putMVar refri (r + 1000) -- repondo 1000 ml de refri
                    threadDelay (1500000)
                    putMVar controle f
                    loop
            else if (r >= 1000) then
                do
                    printf "O cliente %d do refrigerante %s está enchenco seu copo \n" c nome
                    putMVar cliente (c+1) -- proximo cliente do refrigerante pode vir
                    putMVar refri (r - 300) -- consumiu 300 ml do respectivo refrigerante
                    threadDelay (1000000) -- 1000ms = A funcao recebe em microsegundos
                    putMVar controle f
                    loop
            else
                return ()        

            

                            











