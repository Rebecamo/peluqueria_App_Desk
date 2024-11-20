package com.example.peluqueria_app_desk.Modelos;

import com.example.peluqueria_app_desk.Conexion.ConexionDB;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class GestionHorariosModel {
    private int idHorario;
    private int idEmpleado;
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    private String disponible;

    public GestionHorariosModel() {
    }

    public GestionHorariosModel(int idHorario, int idEmpleado, String diaSemana, String horaInicio, String horaFin, String disponible) {
        this.idHorario = idHorario;
        this.idEmpleado = idEmpleado;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.disponible = disponible;
    }

    public int getIdHorario() {
        return idHorario;
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

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "GestionHorariosModel{" +
                "idHorario=" + idHorario +
                ", idEmpleado=" + idEmpleado +
                ", diaSemana='" + diaSemana + '\'' +
                ", horaInicio='" + horaInicio + '\'' +
                ", horaFin='" + horaFin + '\'' +
                ", disponible='" + disponible + '\'' +
                '}';
    }

    public List<GestionHorariosModel> obtenerHorarios() {
        List<GestionHorariosModel> list_horarios = new ArrayList<>();

        try {

            Connection connection = ConexionDB.connection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tbl_horarios");


            while (resultSet.next()) {
                GestionHorariosModel horario = new GestionHorariosModel();
                horario.setIdHorario(resultSet.getInt("idHorario"));
                horario.setIdEmpleado(resultSet.getInt("idEmpleado"));
                horario.setDiaSemana(resultSet.getString("diaSemana"));

                // Leer valores TIME desde la base de datos
                Time horaInicio = resultSet.getTime("horaInicio");
                Time horaFin = resultSet.getTime("horaFin");

                list_horarios.add(horario);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list_horarios;
    }
    public int saveHorario() {
        Connection con = ConexionDB.connection();
        PreparedStatement preparedStatement = null;
        try {

            preparedStatement = con.prepareStatement(
                    "INSERT INTO tbl_horarios(idempleado, diassemana, horainicio, horafin, disponible) VALUES(?, ?, ?, ?, ?)"
            );


            preparedStatement.setInt(1, this.idEmpleado); // idEmpleado
            preparedStatement.setString(2, this.diaSemana); // diaSemana
            preparedStatement.setTime(3, java.sql.Time.valueOf(this.horaInicio)); // horaInicio
            preparedStatement.setTime(4, java.sql.Time.valueOf(this.horaFin)); // horaFin
            preparedStatement.setString(5, this.disponible);


            int retorno = preparedStatement.executeUpdate();
            return retorno;
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar horario: " + e.getMessage(), e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException("Error al cerrar la conexión: " + ex.getMessage(), ex);
            }
        }
    }
    public int editHorario() {
        Connection con = ConexionDB.connection();
        PreparedStatement preparedStatement = null;
        try {

            preparedStatement = con.prepareStatement(
                    "UPDATE tbl_horarios SET idempleado=?, diassemana=?, horainicio=?, horafin=?, disponible=? WHERE idhorario=?"
            );


            preparedStatement.setInt(1, this.idEmpleado); // idEmpleado
            preparedStatement.setString(2, this.diaSemana); // diaSemana
            preparedStatement.setTime(3, java.sql.Time.valueOf(this.horaInicio)); // horaInicio
            preparedStatement.setTime(4, java.sql.Time.valueOf(this.horaFin)); // horaFin
            preparedStatement.setString(5, this.disponible); // disponible (por ejemplo, "Sí" o "No")
            preparedStatement.setInt(6, this.idHorario); // idHorario (criterio de búsqueda)


            int retorno = preparedStatement.executeUpdate();
            return retorno;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar horario: " + e.getMessage(), e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException("Error al cerrar la conexión: " + ex.getMessage(), ex);
            }
        }
    }

    public int deleteHorario(int idHorario) {
        Connection connection = ConexionDB.connection();
        PreparedStatement statement = null;
        try {

            statement = connection.prepareStatement("DELETE FROM tbl_horarios WHERE idhorario=?");
            statement.setInt(1, idHorario);


            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar horario: " + e.getMessage(), e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException("Error al cerrar la conexión: " + ex.getMessage(), ex);
            }
        }
    }
}