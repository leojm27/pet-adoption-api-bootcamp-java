package com.morales.bootcamp.spring_boot_pet_adoption.init;

import com.morales.bootcamp.spring_boot_pet_adoption.models.TipoMascota;
import com.morales.bootcamp.spring_boot_pet_adoption.repository.TipoMascotaRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class LoadDatabase {

    // private static final Logger log = (Logger) LoggerFactory.getLogger(LoadDatabase.class);

    @Transactional // Envuelve las operaciones de escritura en una transacción para garantizar que todo ocurra de manera atómica
    @Bean
    CommandLineRunner initDatabase(TipoMascotaRepository tipoMascotaRepository){
        return args -> {
            // List<TipoMascota> listTipoMascota = tipoMascotaRepository.findAll();
            try{
                if(tipoMascotaRepository.findAll().isEmpty()){
                    System.out.println("intenta crear registros");
                    TipoMascota tipoMascota1 = new TipoMascota("perro");
                    TipoMascota tipoMascota2 = new TipoMascota("gato");
                    // log.info("Carga inicial: " + tipoMascotaRepository.save(tipoMascota1));
                    System.out.println("Carga inicial: " + tipoMascotaRepository.save(tipoMascota1));
                    System.out.println("Carga inicial: " + tipoMascotaRepository.save(tipoMascota2));
                }
            } catch (Exception e){
                e.printStackTrace();
                System.err.println("Error durante la carga inicial: " + e.getMessage());
            }

        };
    }

}
