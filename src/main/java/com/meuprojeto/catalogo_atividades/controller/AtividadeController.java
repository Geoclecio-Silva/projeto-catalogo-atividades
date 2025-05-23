package com.meuprojeto.catalogo_atividades.controller;

import com.meuprojeto.catalogo_atividades.model.*;
import com.meuprojeto.catalogo_atividades.repository.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AtividadeController {

    private final CategoriaRepository categoriaRepo;
    private final UsuarioRepository usuarioRepo;
    private final HabilidadeBNCCRepository habilidadeRepo;
    private final AtividadeRepository atividadeRepo;

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @GetMapping("/atividades/nova")
    public String novaAtividade(Model model) {
        model.addAttribute("atividade", new Atividade());
        model.addAttribute("categorias", categoriaRepo.findAll());
        model.addAttribute("usuarios", usuarioRepo.findAll());
        model.addAttribute("habilidades", habilidadeRepo.findAll());
        return "form-atividade";
    }

    @PostMapping("/atividades/salvar")
    public String salvarAtividade(
            @Valid @ModelAttribute("atividade") Atividade atividade,
            BindingResult result,
            @RequestParam("data") String data,
            @RequestParam("banner") MultipartFile bannerFile,
            @RequestParam("habilidadeIds") List<String> habilidadeIds,
            Model model
    ) throws IOException {

        // Converte a data antes para evitar erros em caso de formatação inválida
        try {
            atividade.setDataRealizacao(LocalDate.parse(data));
        } catch (Exception e) {
            result.rejectValue("dataRealizacao", "error.dataRealizacao", "Data inválida");
        }

        // Valida se pelo menos uma habilidade foi selecionada
        if (habilidadeIds == null || habilidadeIds.isEmpty()) {
            result.rejectValue("habilidades", "error.habilidades", "Selecione ao menos uma habilidade");
        } else {
            atividade.setHabilidades(habilidadeRepo.findAllById(habilidadeIds));
        }

        // Se houver erros, volta para o formulário com os dados preenchidos
        if (result.hasErrors()) {
            model.addAttribute("categorias", categoriaRepo.findAll());
            model.addAttribute("usuarios", usuarioRepo.findAll());
            model.addAttribute("habilidades", habilidadeRepo.findAll());
            return "form-atividade";
        }

        // Upload do banner (se enviado)
        if (!bannerFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + bannerFile.getOriginalFilename();
            File dest = new File(UPLOAD_DIR + fileName);
            dest.getParentFile().mkdirs();
            bannerFile.transferTo(dest);
            atividade.setBannerUrl("/uploads/" + fileName);
        }

        atividadeRepo.save(atividade);
        return "redirect:/atividades/lista";
    }

    @GetMapping("/atividades/lista")
    public String listarAtividades(
            @RequestParam(name = "titulo", required = false) String titulo,
            @RequestParam(name = "categoriaId", required = false) Long categoriaId,
            @RequestParam(name = "habilidadeId", required = false) String habilidadeId,
            Model model
    ) {
        List<Atividade> atividades;

        if (titulo != null && !titulo.isBlank()) {
            atividades = atividadeRepo.findByTituloContainingIgnoreCase(titulo);
        } else if (categoriaId != null) {
            atividades = atividadeRepo.findByCategoriaId(categoriaId);
        } else if (habilidadeId != null && !habilidadeId.isBlank()) {
            atividades = atividadeRepo.findByHabilidadeId(habilidadeId);
        } else {
            atividades = atividadeRepo.findAll();
        }

        model.addAttribute("atividades", atividades);
        model.addAttribute("categorias", categoriaRepo.findAll());
        model.addAttribute("habilidades", habilidadeRepo.findAll());
        return "lista-atividades";
    }


    @GetMapping("/atividades/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Atividade atividade = atividadeRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));
        model.addAttribute("atividade", atividade);
        model.addAttribute("categorias", categoriaRepo.findAll());
        model.addAttribute("usuarios", usuarioRepo.findAll());
        model.addAttribute("habilidades", habilidadeRepo.findAll());
        return "form-atividade";
    }

    @GetMapping("/atividades/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        atividadeRepo.deleteById(id);
        return "redirect:/atividades/lista";
    }

}
