/**
 * @author Sidharthan Jayavelu
 * 
 * Description:
 * 
 * To get the plantUML reverse engineered code
 * 
 */

package com.cinebook.config;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import de.elnarion.util.plantuml.generator.classdiagram.PlantUMLClassDiagramGenerator;
import de.elnarion.util.plantuml.generator.classdiagram.config.PlantUMLClassDiagramConfig;
import de.elnarion.util.plantuml.generator.classdiagram.config.PlantUMLClassDiagramConfigBuilder;

public class DiagramGenerator {
    public static void main(String[] args) throws Exception {
        List<String> scanPackages = List.of(
            "com.cinebook.controller",
            "com.cinebook.service",
            "com.cinebook.model"
//            "com.cinebook.builder",
//            "com.cinebook.command",
//            "com.cinebook.config",
//            "com.cinebook.discountcodefactory",
//            "com.cinebook.interceptor",
//            "com.cinebook.notification",
//            "com.cinebook.observers",
//            "com.cinebook.strategy"
//            "com.cinebook.repository"
        );

        // If you want to blacklist only via deprecated constructor,
        // use the constructor that takes the blacklist regex instead.
        PlantUMLClassDiagramConfig config = new PlantUMLClassDiagramConfigBuilder(scanPackages)
            .withHideClasses(List.of(
                "org.springframework.boot.SpringApplication"
//                "com.cinebook.builder.*",
//                "com.cinebook.dto.*",
//                "com.cinebook.config.*",
//                "com.cinebook.controller.AdminController",
//                "com.cinebook.controller.AdminControllerTest",
//                "com.cinebook.controller.LoyaltyController",
//                "com.cinebook.controller.TheatreController",
//                "com.cinebook.conroller.UserController",
//                "com.cinebook.controller.UserControllerTest"
            ))
            .build();

        PlantUMLClassDiagramGenerator generator = new PlantUMLClassDiagramGenerator(config);
        String uml = generator.generateDiagramText();

        Files.writeString(Paths.get("class-diagram.puml"), uml);

        System.out.println("Generated class-diagram.puml");
    }
}
