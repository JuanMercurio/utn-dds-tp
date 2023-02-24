package utn.ddsG8.impacto_ambiental.domain.Notificaciones;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import utn.ddsG8.impacto_ambiental.domain.estructura.Organizacion;
import utn.ddsG8.impacto_ambiental.repositories.Repositorio;
import utn.ddsG8.impacto_ambiental.repositories.factories.FactoryRepositorio;

public class Notificador implements Job {
    private final Repositorio<Organizacion> repoOrganizacion = FactoryRepositorio.get(Organizacion.class);


    public static void iniciarNotificador()   {

        JobDetail notificacionJob = JobBuilder.newJob(Notificador.class)
                .withIdentity("notiJob", "group1")
                .build();

        Trigger notificacionTrigger = TriggerBuilder.newTrigger()
                .withIdentity("notiTrigger", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/5 * * * ?"))
                .build();

        Scheduler scheduler;
        try {
            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(notificacionJob, notificacionTrigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        repoOrganizacion.buscarTodos().forEach(o -> o.notificarContactos());
    }
}
