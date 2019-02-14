/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagio.controller;

import estagio.dao.CategoriaDAO;
import estagio.model.Categoria;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import estagio.view.util.TextFieldFormatterHelper;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Pereira
 */
public class CategoriaController implements Initializable {

    @FXML
    private JFXTextField txt_codigo;
    @FXML
    private JFXTextField txt_nome;
    @FXML
    private JFXButton btn_Buscar;
    @FXML
    private JFXButton btn_Novo;
    @FXML
    private JFXButton btn_Gravar;
    @FXML
    private JFXButton btn_Excluir;
    @FXML
    private JFXButton btn_Cancelar;
    @FXML
    private JFXButton btn_Sair;    
    @FXML
    private ContextMenu ctm_nome;
    @FXML
    private MenuItem mi_nome;
    @FXML
    private Tooltip ttp_Nome;
    @FXML
    private Tooltip ttp_btnBuscar;
    @FXML
    private Tooltip ttp_btnNovo;
    @FXML
    private Tooltip ttp_btnGravar;
    @FXML
    private Tooltip ttp_btnExcluir;
    @FXML
    private Tooltip ttp_btnCancelar;
    @FXML
    private Tooltip ttp_btnSair;  
    String corErro = "-fx-border-color: red;";
    String corNormal = "-fx-border-color:white";
    TextFieldFormatterHelper tffh;
    @FXML
    private Label lbl_codigo;
    @FXML
    private Tooltip ttp_lblCodigo;
    @FXML
    private Label lbl_nome;
    @FXML
    private AnchorPane ap_busca;
    @FXML
    private JFXButton btn_filtro;
    @FXML
    private JFXTextField txt_filtro;
    @FXML
    private TableView<Categoria> tb_categoria;
    @FXML
    private TableColumn<Categoria, String> tc_codigo;
    @FXML
    private TableColumn<Categoria, String> tc_nome;
    @FXML
    private JFXButton btn_voltar;
    private Categoria categoria;
    private CategoriaDAO categoriaDAO;
    private ObservableList<Categoria> obslCategoria;
    private List<Categoria> listaCategoria;
    @FXML
    private AnchorPane ap_categoria;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       desativaTela();
       categoria = new Categoria();
       categoriaDAO = new CategoriaDAO();
       txt_codigo.setPromptText("Código da Categória");
       txt_nome.setPromptText("Exemplo:Bebidas...");
       mi_nome.setText("Por favor, insira o nome corretamente.");
       ctm_nome.getItems().add(mi_nome);
       txt_nome.setTextFormatter(tffh.getUpperCaseTextFieldFormatter());
       txt_filtro.setTextFormatter(tffh.getUpperCaseTextFieldFormatter());
       listaCategoria = new ArrayList<>();
       
    }    
    
    public void desativaTela()
    {
       txt_nome.setText("");
       txt_codigo.setText("0");
       btn_Excluir.setDisable(true);
       btn_Cancelar.setDisable(true);
       categoria = new Categoria();
       categoriaDAO = new CategoriaDAO();
    }
    
    public void ativaTela()
    {
       btn_Excluir.setDisable(false);
       btn_Cancelar.setDisable(false);
    }
    @FXML
    private void OnActionGravar(ActionEvent event) {
        Boolean erro=false;

        
        if (txt_nome.getText().equals("") == true || txt_nome.getText().length() < 4) {
            erro = true;
            ctm_nome.show(txt_nome,Side.RIGHT,10,0);
            txt_nome.setStyle(corErro);
        }
        else
        {
            categoria.setNome(txt_nome.getText());
            txt_nome.setStyle(corNormal);
            ctm_nome.hide();
        }
        
        if (txt_codigo.getText().equals("") == true) {
            erro = true;
        }
        else
        {
            categoria.setId(Long.parseLong(txt_codigo.getText()));
        }
        
        if (erro != true) 
        {
            categoriaDAO = new CategoriaDAO();
            ButtonType btnNao = new ButtonType("Okay");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");            
            if (categoria.getId() == 0) 
            {
                categoria.setId(null);
                categoriaDAO.inserir(categoria);
                alert.setContentText("Categoria "+categoria.getNome()+" Inserida");
            }
            else
            {
                categoriaDAO.alterar(categoria);
                alert.setContentText("Categoria "+categoria.getNome()+" Alterada");
            }
            alert.show();
            desativaTela();
        }
    }

    @FXML
    private void OnActionExcluir(ActionEvent event) 
    {
        
        Alert dialogoExe = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType btnSim = new ButtonType("Sim");
        ButtonType btnNao = new ButtonType("Não");
        dialogoExe.setTitle("");
        dialogoExe.setHeaderText("Você deseja realmente excluir "+categoria.getNome()+"?");
        dialogoExe.getButtonTypes().setAll(btnSim, btnNao);
        dialogoExe.showAndWait().ifPresent(b -> 
        {
            if (b == btnSim)
            {
                if (txt_codigo.getText().equals("") != true && !txt_codigo.getText().isEmpty()) 
                {
                  categoria.setId(Long.parseLong(txt_codigo.getText()));
                  categoriaDAO.Deletar(categoria);
                  Alert alert = new Alert(Alert.AlertType.INFORMATION);
                  alert.setContentText("Categoria "+categoria.getNome()+" Excluido");
                  alert.show();
                }     
                desativaTela();
            }          
        });      
    }

    @FXML
    private void OnActionCancelar(ActionEvent event) 
    {
       desativaTela();
    }

    @FXML
    private void OnActionSair(ActionEvent event) throws IOException 
    {
        ap_categoria.setVisible(false);
    }

    @FXML
    private void OnActionNovo(ActionEvent event) 
    {
       desativaTela();
    }

    public void setCategoria(Categoria categoria)
    {
        this.categoria = categoria;
        txt_nome.setText(categoria.getNome());
        txt_codigo.setText(""+categoria.getId());
        limpaBuscas();
        ativaTela();
    }
    
    public Categoria getCategoria()
    {
        return categoria;
    }    
    
    @FXML
    private void OnActionBuscar(ActionEvent event) {        
        ap_busca.setVisible(true);
        carregaTela(txt_filtro.getText());
    }

    @FXML
    private void Limitetxt_nome(KeyEvent event) {
        if (txt_nome.getText().length() == 100)
            event.consume();
    }

    @FXML
    private void OnActionFiltro(ActionEvent event) {
        carregaTela(txt_filtro.getText());
    }

    @FXML
    private void OnKeyPressedEnter(KeyEvent event) {
        txt_filtro.setOnKeyPressed((KeyEvent event1) -> {
            if (event1.getCode() == KeyCode.ENTER) {
                carregaTela(txt_filtro.getText());
            }
        });
    }
    
    public void carregaTela(String palavra)
    {
         listaCategoria.clear();
         tb_categoria.getItems().clear();
         tc_codigo.setCellValueFactory(new PropertyValueFactory<>("Id"));
         tc_nome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
         listaCategoria = categoriaDAO.listar(palavra.toUpperCase());
         if (!listaCategoria.isEmpty()) 
         {
             obslCategoria = FXCollections.observableArrayList(listaCategoria);
             tb_categoria.setItems(obslCategoria);
        }

    }

    @FXML
    private void Limitetxt_filtro(KeyEvent event) {
        if (txt_filtro.getText().length() == 100) {
            event.consume();
        }
    }

    @FXML
    private void OnMouseClickedCategoria(MouseEvent event) {
       if (tb_categoria.getSelectionModel().getSelectedItem() != null) 
       {
           this.setCategoria(tb_categoria.getSelectionModel().getSelectedItem());
       }
    }

    @FXML
    private void OnActionVoltar(ActionEvent event) {
        limpaBuscas();
    }
    
    public void limpaBuscas()
    {
        listaCategoria.clear();
        tb_categoria.getItems().clear();
        ap_busca.setVisible(false);
        txt_filtro.setText("");
    }
}