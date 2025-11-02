package cl.municipalidadchillan.dideco.service;

import java.util.List;

import cl.municipalidadchillan.dideco.model.DocumentoRespaldo;

public interface DocumentoRespaldoService {
    List<DocumentoRespaldo> obtenerTodos();
    List<DocumentoRespaldo> obtenerPorIdAvance(int idAvance);
    DocumentoRespaldo obtenerPorId(Integer id);
    DocumentoRespaldo guardar(DocumentoRespaldo documento);
    void eliminar(Integer id);
}