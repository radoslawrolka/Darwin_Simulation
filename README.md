# Darwin_Simulation
Darwin Simulation Project in Java with JavaFX GUI and Multithreading

More info about the project here: https://github.com/apohllo/obiektowe-lab/tree/master/proj1

# Configuration

While starting this app you can either:
- fill menu page with all required data,
- use CSV file (safe mode: files are availible only from ```src/resources/<filename>.csv```). (check in gallery below to see how to run setup from file)

[data entry order]
  
1. mapWidth,
  
2. mapHeight,
  
3. mapBorder ("Globe"/"Hell") # if "Hell", then penalty (int) required,
  
4. mapBiome ("Equator"/"Toxic") # if "Equator", then (int)equatorWidth and (int)equatorHeight required,
   
5. plants energy,

6. starting number of plants,

7. spawned plants per day,

8. animals energy required to reproduce,

9. starting number of animals,

10. maximal energy of animal,

11. length of genotype,

12. max number of mutations,

13. min number of mutations,

14. move delay (in miliseconds) # 1000 == 1 second,

15. energy cost per day

16. moving style ("FP"/"BR") # Fully_predestined or Bit_of_Randomness,

17. mutation style ("FR"/"LC") # Fully_random or Little_correction.

Example in ```src/main/resources/Zeszyt1.csv```

# Simulation 

During the simulation you can:
- pause/resume the game,
- start a new game,
- change graphics (basics names, classic, beautiful),
- check actual statistics,
- follow choosen animal and see its stats.

# Output

After the game you can analise the data in CSV file.

(right now only in ```src/main/resources/statistics.csv```)

# Gallery
- Menu window:
<img src="/src/main/resources/Menu.png" width="600"/>
- Smooth graphic settings:
<img src="/src/main/resources/Smooth.png" width="600"/>
- Classic graphic settings:
<img src="/src/main/resources/Classic.png" width="600"/>
- Beautiuf graphic settings (MustSee!!!):
<img src="/src/main/resources/Beautiful_graphics.png" width="600"/>
