package com.example.peluqueria_app_desk;
import com.example.peluqueria_app_desk.Conexion.ConexionDB;
import com.example.peluqueria_app_desk.Modelos.HorariosModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class HorariosController {

    @FXML
    private TableView<HorariosModel> tblHorarios;

    @FXML
    private TableColumn<HorariosModel, Integer> clidHorario;

    @FXML
    private TableColumn<HorariosModel, Integer> clidEmpleado;

    @FXML
    private TableColumn<HorariosModel, String> clDiaSemana;

    @FXML
    private TableColumn<HorariosModel, String> clHoraEntrada;

    @FXML
    private TableColumn<HorariosModel, String> clHoraSalida;

    @FXML
    private ListView<String> listViewDia;

    @FXML
    private TextField txtHoraInicio;

    @FXML
    private TextField txtHoraFinal;
    @FXML
    private Label lblNombreEmpleado;

    private ObservableList<HorariosModel> horariosList;
    private int empleadoId;
    @FXML
    public void initialize() {
        // Configurar las columnas de la tabla
        clidHorario.setCellValueFactory(new PropertyValueFactory<>("idHorario"));
        clidEmpleado.setCellValueFactory(new PropertyValueFactory<>("idEmpleado"));
        clDiaSemana.setCellValueFactory(new PropertyValueFactory<>("diaSemana"));
        clHoraEntrada.setCellValueFactory(new PropertyValueFactory<>("horaInicio"));
        clHoraSalida.setCellValueFactory(new PropertyValueFactory<>("horaFin"));

        horariosList = FXCollections.observableArrayList();
        tblHorarios.setItems(horariosList);

        // Poblar el ListView con los días de la semana
        listViewDia.setItems(FXCollections.observableArrayList(
                "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"
        ));
        listViewDia.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
        cargarHorarios();
    }
    @FXML
    private void agregarHorario() {
        ObservableList<String> diasSeleccionados = listViewDia.getSelectionModel().getSelectedItems();
        String horaInicio = txtHoraInicio.getText();
        String horaFin = txtHoraFinal.getText();

        if (diasSeleccionados.isEmpty() || horaInicio.isEmpty() || horaFin.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios.");
            return;
        }

        try (Connection connection = ConexionDB.connection()) {
            for (String dia : diasSeleccionados) {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO tbl_horarios (id_empleado, dia_semana, hora_inicio, hora_fin) VALUES (?, ?, ?, ?)"
                );
                statement.setInt(1, empleadoId);
                statement.setString(2, dia);
                statement.setString(3, horaInicio);
                statement.setString(4, horaFin);
                statement.executeUpdate();
            }
            cargarHorarios();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarHorarios() {
        horariosList.clear(); // Limpiar la lista antes de cargar datos

        String query = "SELECT * FROM tbl_horarios WHERE id_empleado = ?";
        try (Connection connection = ConexionDB.connection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, empleadoId); // Filtrar por empleado
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                horariosList.add(new HorariosModel(
                        resultSet.getInt("id_horario"),
                        resultSet.getInt("id_empleado"), // Incluye el id_empleado
                        resultSet.getString("dia_semana"),
                        resultSet.getTime("hora_inicio").toString(),
                        resultSet.getTime("hora_fin").toString()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tblHorarios.setItems(horariosList);
    }


    @FXML
    private void eliminarHorario() {
        HorariosModel horarioSeleccionado = tblHorarios.getSelectionModel().getSelectedItem();

        if (horarioSeleccionado == null) {
            mostrarAlerta("Error", "Selecciona un horario para eliminar.");
            return;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/RAGlamStudio1", "postgres", "password");
             PreparedStatement statement = connection.prepareStatement("DELETE FROM tbl_horarios WHERE id_horario = ?")) {

            statement.setInt(1, horarioSeleccionado.getIdHorario());
            statement.executeUpdate();

            cargarHorarios();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void modificarHorario() {
        HorariosModel horarioSeleccionado = tblHorarios.getSelectionModel().getSelectedItem();

        if (horarioSeleccionado == null) {
            mostrarAlerta("Error", "Selecciona un horario para modificar.");
            return;
        }

        String horaInicio = txtHoraInicio.getText();
        String horaFin = txtHoraFinal.getText();

        if (horaInicio.isEmpty() || horaFin.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios.");
            return;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/RAGlamStudio1", "postgres", "password");
             PreparedStatement statement = connection.prepareStatement("UPDATE tbl_horarios SET hora_inicio = ?, hora_fin = ? WHERE id_horario = ?")) {

            statement.setString(1, horaInicio);
            statement.setString(2, horaFin);
            statement.setInt(3, horarioSeleccionado.getIdHorario());
            statement.executeUpdate();

            cargarHorarios();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // ID del empleado que inició sesión





    }