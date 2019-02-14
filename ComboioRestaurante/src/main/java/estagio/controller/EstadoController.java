/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagio.controller;

import estagio.dao.EstadoDAO;
import estagio.model.Estado;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import estagio.executar.Estagio;
import estagio.view.util.TextFieldFormatterHelper;
import estagio.view.util.UISetup;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Pereira
 */
public class EstadoController implements Initializable {

    @FXML
    private JFXTextField txt_codigo;
    @FXML
    private JFXButton btn_Buscar;
    @FXML
    private JFXTextField txt_nome;
    @FXML
    private JFXTextField txt_sigla;
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
    private Tooltip ttp_codigo;
    @FXML
    private Tooltip ttp_btnBuscar;
    @FXML
    private Tooltip ttp_nome;
    @FXML
    private Tooltip ttp_sigla;
    @FXML
    private Tooltip ttp_btnGravar;
    @FXML
    private Tooltip ttp_btnExcluir;
    @FXML
    private Tooltip ttp_btnCancelar;
    @FXML
    private Tooltip ttp_btnSair;
    @FXML
    private Tooltip ttp_btnNovo;
    private StackPane stackPane;
    @FXML
    private ContextMenu ctm_nome;
    @FXML
    private MenuItem mi_nome;
    @FXML
    private Tooltip ttp_Nome;
    @FXML
    private ContextMenu ctm_sigla;
    @FXML
    private MenuItem mi_sigla;
    @FXML
    private Tooltip ttp_Sigla;
    @FXML
    private AnchorPane ap_busca;
    @FXML
    private JFXButton btn_filtro;
    @FXML
    private JFXTextField txt_filtro;
    @FXML
    private TableView<Estado> tb_estado;
    @FXML
    private TableColumn<Estado, String> tc_codigo;
    @FXML
    private TableColumn<Estado, String> tc_nome;
    @FXML
    private TableColumn<Estado, String> tc_sigla;
    private Estado estado;
    private EstadoDAO estadoDAO;
    @FXML
    private JFXButton btn_voltar;
    private String corErro = "-fx-border-color: red;";
    private String corNormal = "-fx-border-color:white";
    private TextFieldFormatterHelper tffh;
    private ObservableList<Estado> obslEstado;
    private List<Estado> listaEstado;
    @FXML
    private AnchorPane ap_estado;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        estado = new Estado();
        resetaDAO();
        txt_nome.setPromptText("SÃO PAULO");
        txt_sigla.setPromptText("SP");
        desativaTela();
        txt_nome.setTextFormatter(tffh.getUpperCaseTextFieldFormatter());
        txt_sigla.setTextFormatter(tffh.getUpperCaseTextFieldFormatter());
        txt_filtro.setTextFormatter(tffh.getUpperCaseTextFieldFormatter());
        listaEstado = new ArrayList<>();                
    }    
    
    public void resetaDAO()
    {
        estadoDAO = new EstadoDAO();
    }
    
    public void desativaTela()
    {
       txt_nome.setText("");
       txt_sigla.setText("");       
       txt_codigo.setText("0");
       btn_Excluir.setDisable(true);
       btn_Cancelar.setDisable(true);
       txt_nome.setStyle(corNormal);
       txt_sigla.setStyle(corNormal);
       ctm_nome.hide();
       ctm_sigla.hide();
    }

    @FXML
    private void OnActionBuscar(ActionEvent event) {
        ap_busca.setVisible(true);
        carregaTela(txt_filtro.getText());
        
    }

    @FXML
    private void OnActionNovo(ActionEvent event) {
        estado = new Estado();
        resetaDAO();
        desativaTela();
    }

    @FXML
    private void OnActionGravar(ActionEvent event) {
        Boolean erro=false;
        estado = new Estado();
        resetaDAO();
        String nome = txt_nome.getText();
        nome = nome.replaceAll("\\s","");
        if (txt_nome.getText().equals("") == true || nome.equals("") == true || nome.length()<3) 
        {
            txt_nome.setStyle(corErro);
            ctm_nome.show(txt_nome,Side.RIGHT,10,0);
            erro = true;
        }
        else
        {
            txt_nome.setStyle(corNormal);
            estado.setNome(txt_nome.getText());
            ctm_nome.hide();
        }
        if (txt_sigla.getText().equals("") == true || txt_sigla.getText().length()!=2) 
        {
            erro = true;
            ctm_sigla.show(txt_sigla,Side.RIGHT,10,0);
            txt_sigla.setStyle(corErro);
        }
        else     
        {
            estado.setUf(txt_sigla.getText());
            txt_sigla.setStyle(corNormal);
            ctm_sigla.hide();
        }
        if (txt_codigo.getText().equals("")) {
            erro = true;
        }
        else
        {
            estado.setId(Long.parseLong(txt_codigo.getText()));
        }
        if (erro != true) 
        {
            Alert dialogoExe = new Alert(Alert.AlertType.INFORMATION);
            dialogoExe.setTitle("Gravar");
                       
            if (estado.getId()==0)
            {               
                estado.setId(null);
                estadoDAO.inserir(estado);      
                dialogoExe.setHeaderText("Estado: "+estado.getNome()+" inserida");
            }
            else
            {
                estadoDAO.alterar(estado);
                dialogoExe.setHeaderText("Estado: "+estado.getNome()+" Alterada");
            }
            dialogoExe.showAndWait();
            desativaTela();
            resetaDAO();
            
        }
    }
    public void setEstado(Estado estado)
    {
        this.estado = estado;
        txt_nome.setText(estado.getNome());
        txt_codigo.setText(""+estado.getId());
        txt_sigla.setText(estado.getUf());        
        ativaTela();        
    }
    
    public void ativaTela()
    {
       btn_Excluir.setDisable(false);
       btn_Cancelar.setDisable(false);
    }    
    public Estado getEstado()
    {
        return estado;
    }
    @FXML
    private void OnActionExcluir(ActionEvent event) {
        resetaDAO();
        Alert dialogoExe = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType btnSim = new ButtonType("Sim");
        ButtonType btnNao = new ButtonType("Não");
        dialogoExe.setTitle("");
        dialogoExe.setHeaderText("Você deseja realmente excluir "+estado.getNome()+"?");
        dialogoExe.getButtonTypes().setAll(btnSim, btnNao);
        dialogoExe.showAndWait().ifPresent(b -> 
        {
            if (b == btnSim)
            {
                if (txt_codigo.getText().equals("") != true) 
                {
                  Boolean deletado = estadoDAO.Deletar(estado);
                  Alert alert = new Alert(Alert.AlertType.INFORMATION);                  
                  if (deletado == true) 
                  {                        
                    alert.setContentText("Estado "+estado.getNome()+" Excluido");
                    alert.show();             
                    desativaTela();
                  }
                  else
                  {
                    alert.setContentText("Estado "+estado.getNome()+" não pode ser excluido, verifique se há cidades com o estado.");
                    alert.show();       
                  }
                }     
            }
        });
    }

    @FXML
    private void OnActionCancelar(ActionEvent event) {
        estado = new Estado();
        estadoDAO = new EstadoDAO();
        desativaTela();
    }

    @FXML
    private void OnActionSair(ActionEvent event) {
        ap_estado.setVisible(false);
    }
    
    @FXML
    private void Limitetxt_Nome(KeyEvent event) {
        if (txt_nome.getText().length() == 100 )
            event.consume();
        
        txt_nome.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("\\sa-zA-Z-0-9*")) {
            txt_nome.setText(newValue.replaceAll("[^\\sa-zA-Z-0-9]", ""));
        }
    });
    }

    @FXML
    private void Limitetxt_Sigla(KeyEvent event) {
        if (txt_sigla.getText().length() == 2)
            event.consume();        
    txt_sigla.textProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue.matches("a-zA-Z*")) {
            txt_sigla.setText(newValue.replaceAll("[^a-zA-Z]", ""));
        }
    });
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
        listaEstado.clear();
        tb_estado.getItems().clear();
        tc_codigo.setCellValueFactory(new PropertyValueFactory<>("Id"));
        tc_nome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        tc_sigla.setCellValueFactory(new PropertyValueFactory<>("Uf"));
        listaEstado = estadoDAO.listar(palavra.toUpperCase());
        if (!listaEstado.isEmpty()) 
        {
            obslEstado = FXCollections.observableArrayList(listaEstado);
            tb_estado.setItems(obslEstado);
        }

   }

    @FXML
    private void Limitetxt_filtro(KeyEvent event) {
        if (txt_filtro.getText().length() == 100)            
            event.consume();
    }   

    @FXML
    private void OnMouseClickedEstado(MouseEvent event) {        
        if (tb_estado.getSelectionModel().getSelectedItem() != null) 
        {
           this.setEstado(tb_estado.getSelectionModel().getSelectedItem());
           limpaBuscas();
        }               
    }

    @FXML
    private void OnActionVoltar(ActionEvent event) {
        limpaBuscas();
    }
     
    public void limpaBuscas()
    {
        listaEstado.clear();
        tb_estado.getItems().clear();
        ap_busca.setVisible(false);
        txt_filtro.setText("");
    }
    
    
}
