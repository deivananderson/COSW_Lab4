/*
 * Copyright (C) 2016 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.cosw.jpa.sample;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author hcadavid
 */
public class SimpleMainApp {
   
    public static void main(String a[]){
        SessionFactory sf=getSessionFactory();
        Session s=sf.openSession();
        Transaction tx=s.beginTransaction();

        Estudiante est1 = new Estudiante(2093715, "Deivan Uno");
        Estudiante est2 = new Estudiante(2093714, "Deivan Dos");
        Curso cur1 = new Curso(20022017, "Soluciones de Software 2017 - 1", "SOSW");
        Curso cur2 = new Curso(21022017, "Construcción de Software 2017 - 1", "COSUU");

        Set<Curso> cursos = new HashSet<>();
        Set<Estudiante> estudiantes = new HashSet<>();

        cursos.add(cur1); cursos.add(cur2);
        estudiantes.add(est1); estudiantes.add(est2);

        est1.setCursos(cursos); est2.setCursos(cursos);
        cur1.setEstudianteses(estudiantes); cur2.setEstudianteses(estudiantes);

        s.saveOrUpdate(est1);
        s.saveOrUpdate(est2);

        tx.commit(); 
        s.close();
        sf.close();
    }

    public static SessionFactory getSessionFactory() {
        // loads configuration and mappings
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        ServiceRegistry serviceRegistry
                = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        // builds a session factory from the service registry
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }

}
