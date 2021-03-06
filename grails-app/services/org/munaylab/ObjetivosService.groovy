package org.munaylab

import org.munaylab.ods.Indicador
import org.munaylab.ods.Meta
import org.munaylab.ods.Objetivo
import org.munaylab.osc.Organizacion

import grails.gorm.transactions.Transactional

@Transactional
class ObjetivosService {

    List<Objetivo> obtenerObjetivosOds() {
        return Objetivo.all
    }

    Objetivo guardarObjetivoDesdeConfiguracion(objetivoConfig) {
        Objetivo objetivoOds = Objetivo.findByPosicion(objetivoConfig.posicion)

        if (objetivoOds) return objetivoOds

        objetivoOds = new Objetivo().with {
            posicion = objetivoConfig.posicion
            nombre = objetivoConfig.nombre
            objetivo = objetivoConfig.objetivo
            descripcion = objetivoConfig.descripcion
            it
        }
        objetivoOds.save()

        return objetivoOds
    }

    Organizacion agregarObjetivo(Organizacion org, Long idObjetivo) {
        if (!idObjetivo || !org) return null

        Objetivo objetivo = Objetivo.get(idObjetivo)
        if (!objetivo) return null

        if (org?.objetivos?.contains(objetivo)) return org

        org.addToObjetivos(objetivo)
        org.save()

        return org
    }

    void eliminarObjetivo(Organizacion org, Long idObjetivo) {
        if (!idObjetivo) return

        Objetivo objetivo = Objetivo.get(idObjetivo)
        if (!objetivo) return

        if (!org?.objetivos?.contains(objetivo)) return

        org.removeFromObjetivos(objetivo)
        org.save()
    }

}
