package com.example.peluqueria_app_desk.Modelos;

import com.example.peluqueria_app_desk.Conexion.ConexionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.format.DateTimeFormatter;


public class HorariosModel {
    private int idHorario;
    private int idEmpleado; // Nuevo campo
    private String diaSemana;
    private String horaInicio;
    private String horaFin;


    public HorariosModel(int idHorario, int idEmpleado, String diaSemana, String horaInicio, String horaFin) {
        this.idHorario = idHorario;
        this.idEmpleado = idEmpleado;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public HorariosModel() {
    }

    // Getters y setters
    public int getIdHorario() {
        return idHorario;
    }



    public String getDiaSemana() {
        return diaSemana;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }
    public ObservableList<HorariosModel> getHorarios(int empleadoId) {
        Connection connection = null;
        try {
            connection = ConexionDB.connection();

            ObservableList<HorariosModel> dataHorarios = FXCollections.observableArrayList();

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM tbl_horarios;");

            while (resultSet.next()) {
                HorariosModel horarioModel = new HorariosModel();


                horarioModel.setIdHorario(resultSet.getInt("id_horario"));
                horarioModel.setIdEmpleado(resultSet.getInt("id_empleado")); // Nuevo campo
                horarioModel.setDiaSemana(resultSet.getString("dia_semana"));

              //pasar horainicio y horafin a formato HH:mm
                Time horaInicio = resultSet.getTime("hora_inicio");
                Time horaFin = resultSet.getTime("hora_fin");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                horarioModel.setHoraInicio(horaInicio.toLocalTime().format(formatter));
                horarioModel.setHoraFin(horaFin.toLocalTime().format(formatter));

                dataHorarios.add(horarioModel);
            }

            return dataHorarios;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public int saveHorario() {
        Connection con = null;
        try {
            con = ConexionDB.connection();

            PreparedStatement preparedStatement = null;

            preparedStatement = con.prepareStatement(
                    "INSERT INTO tbl_horarios(id_empleado, dia_semana, hora_inicio, hora_fin) VALUES (?, ?, ?, ?);"
            );

            preparedStatement.setInt(1, this.idEmpleado); // Campo idEmpleado
            preparedStatement.setString(2, this.diaSemana); // Campo diaSemana
            preparedStatement.setTime(3, java.sql.Time.valueOf(this.horaInicio + ":00")); // Campo horaInicio
            preparedStatement.setTime(4, java.sql.Time.valueOf(this.horaFin + ":00")); // Campo horaFin

            int retorno = preparedStatement.executeUpdate(); // Ejecuta la consulta
            return retorno;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int editHorario() {
        Connection con = null;
        try {
            // Conexión a la base de datos
            con = ConexionDB.connection();

            // Preparar la consulta SQL
            PreparedStatement preparedStatement = con.prepareStatement(
                    "UPDATE tbl_horarios SET id_empleado=?, dia_semana=?, hora_inicio=?, hora_fin=? WHERE id_horario=?;"
            );

            // Asignar valores a los parámetros de la consulta
            preparedStatement.setInt(1, this.idEmpleado); // id_empleado
            preparedStatement.setString(2, this.diaSemana); // dia_semana


            if (this.horaInicio != null) {
                if (!this.horaInicio.contains(":")) {
                    throw new IllegalArgumentException("Formato de hora inválido. Debe contener ':'");
                }
                if (this.horaInicio.length() == 5) {
                    this.horaInicio = this.horaInicio + ":00"; // Agregar segundos si faltan
                }
            } else {
                throw new IllegalArgumentException("La hora de inicio no puede ser nula");
            }

            preparedStatement.setTime(3, java.sql.Time.valueOf(this.horaInicio)); // hora_inicio


            if (this.horaFin != null) {
                if (!this.horaFin.contains(":")) {
                    throw new IllegalArgumentException("Formato de hora inválido. Debe contener ':'");
                }
                if (this.horaFin.length() == 5) {
                    this.horaFin = this.horaFin + ":00"; // Agregar segundos si faltan
                }
            } else {
                throw new IllegalArgumentException("La hora de fin no puede ser nula");
            }

            preparedStatement.setTime(4, java.sql.Time.valueOf(this.horaFin)); // hora_fin

            // Asignar el ID del horario
            preparedStatement.setInt(5, this.idHorario); // id_horario

            // Ejecutar la consulta
            int retorno = preparedStatement.executeUpdate();
            if (retorno == 0) {
                throw new RuntimeException("No se pudo actualizar el horario. Verifica que el ID del horario sea correcto.");
            }
            return retorno;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar el horario en la base de datos: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Error en los parámetros del horario: " + e.getMessage(), e);
        } finally {
            // Cerrar la conexión
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int deleteHorario() {
        Connection con = null;
        try {
            // Establecer la conexión con la base de datos
            con = ConexionDB.connection();

            // Validar que el ID del horario sea válido
            if (this.idHorario <= 0) {
                throw new IllegalArgumentException("El ID del horario debe ser mayor a cero.");
            }

            // Preparar la consulta SQL para eliminar el registro
            PreparedStatement statement = con.prepareStatement("DELETE FROM tbl_horarios WHERE id_horario = ?;");
            statement.setInt(1, this.idHorario);

            // Ejecutar la consulta
            int resultado = statement.executeUpdate();
            if (resultado == 0) {
                throw new RuntimeException("No se pudo eliminar el horario. Verifica que el ID del horario sea correcto.");
            }
            return resultado;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar el horario en la base de datos: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Error en los parámetros del horario: " + e.getMessage(), e);
        } finally {
            // Asegurarse de cerrar la conexión
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
