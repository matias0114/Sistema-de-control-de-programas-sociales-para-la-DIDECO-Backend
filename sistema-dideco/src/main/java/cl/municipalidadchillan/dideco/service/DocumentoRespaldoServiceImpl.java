package cl.municipalidadchillan.dideco.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.municipalidadchillan.dideco.model.DocumentoRespaldo;
import cl.municipalidadchillan.dideco.repository.DocumentoRespaldoRepository;

@Service
public class DocumentoRespaldoServiceImpl implements DocumentoRespaldoService {

    @Autowired
    private DocumentoRespaldoRepository documentoRespaldoRepository;

    @Override
    public List<DocumentoRespaldo> obtenerTodos() {
        return documentoRespaldoRepository.findAll();
    }

    @Override
    public List<DocumentoRespaldo> obtenerPorIdAvance(int idAvance) {
        return documentoRespaldoRepository.findByAvance_IdAvance(idAvance);
    }

    @Override
    public DocumentoRespaldo obtenerPorId(Integer id) {
        Optional<DocumentoRespaldo> doc = documentoRespaldoRepository.findById(id);
        return doc.orElse(null);
    }

    @Override
    public DocumentoRespaldo guardar(DocumentoRespaldo documento) {
        return documentoRespaldoRepository.save(documento);
    }

    @Override
    public void eliminar(Integer id) {
        documentoRespaldoRepository.deleteById(id);
    }
}