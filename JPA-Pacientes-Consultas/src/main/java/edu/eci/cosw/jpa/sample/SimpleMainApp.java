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

import edu.eci.cosw.jpa.sample.model.Consulta;
import edu.eci.cosw.jpa.sample.model.Paciente;
import edu.eci.cosw.jpa.sample.model.PacienteId;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Date;
import java.util.Set;

/**
 *
 * @author hcadavid
 */
public class SimpleMainApp {
   
    public static void main(String a[]){
        SessionFactory sf=getSessionFactory();
        Session s=sf.openSession();
        Transaction tx=s.beginTransaction();

        Paciente paciente = (Paciente) s.load(Paciente.class,new PacienteId(1,"cc"));
        System.out.println(paciente.getNombre());

        Consulta c = new Consulta();
        c.setResumen("Nombre: Deivan Anderson");
        c.setFechaYHora(new Date(2017,02,20));
        Set<Consulta> consultasP =  paciente.getConsultas();
        consultasP.add(c);
        paciente.setConsultas(consultasP);
        s.saveOrUpdate(paciente);

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
