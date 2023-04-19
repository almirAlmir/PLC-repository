--PokeBattle Raichu VS Pikachu
local function vai_pikachu()
    return math.random(20) -- Ataque aleatório ponderado
end


local function vai_raichu()
    return math.random(20) -- Ataque aleatório ponderado
end

local function battle(pikachu_hp, raichu_hp)
  local turno = 1
  while pikachu_hp > 0 and raichu_hp > 0 do
    print("Turno " .. turno)
      
      -- Pikachu ataca primeiro
    print("Pikachu ataca!")
    local ataque = vai_pikachu()
    local dano = 0
    local nome_ataque = "empty"

    if ataque <= 10 then
        dano = 50
        nome_ataque = "Choque do trovão"
    end
    if ataque <= 15 and ataque > 10 then
        dano = 100
        nome_ataque = "Cauda de ferro"
    end
    if ataque <= 18 and ataque > 15 then
        dano = (150)
        nome_ataque = "Investida trovão"
    end
    if ataque >= 19 then
        dano = 200
        nome_ataque = "Trovão"
    end
    raichu_hp = raichu_hp - dano
    print("Pikachu utilizou: "..nome_ataque)
    print("HP Raichu: " .. raichu_hp)
    print("HP Pikachu: " .. pikachu_hp)
      
      -- raichu morreu?
    if raichu_hp <= 0 then
      print("Raichu foi derrotado! Pikachu venceu!")
    return
    end

    -- Raichu ataca
    local ataque = vai_raichu()
    local dano = 0
    local nome_ataque = "empty"

    if ataque <= 10 then
        dano = 50
        nome_ataque = "Choque do trovão"
    end
    if ataque <= 15 and ataque > 10 then
        dano = 100
        nome_ataque = "Cauda de ferro"
    end
    if ataque <= 18 and ataque > 15 then
        dano = (150)
        nome_ataque = "Investida trovão"
    end
    if ataque >= 19 then
        dano = 200
        nome_ataque = "Trovão"
    end
    pikachu_hp = pikachu_hp - dano
    print("Raichu utilizou: "..nome_ataque)
    print("HP Raichu: " .. raichu_hp)
    print("HP Pikachu: " .. pikachu_hp)
    
    -- pikachu morreu?
    if pikachu_hp <= 0 then
      print("Pikachu foi derrotado! Raichu venceu!")
      return

    end
    turno = turno + 1
  end
end

-- Cria a coroutine da batalha
local pokebattle = coroutine.create(function()
  battle(800, 1000) -- Hp de Pikachu e Raichu respectivamente
end)



-- Executa as coroutines até que uma delas termine

coroutine.resume(pokebattle)

