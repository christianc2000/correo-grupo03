/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grupo03sa_mail;

import Negocio.NUser;
import java.util.List;

/**
 *
 * @author WorkcorpDev
 */
public class Manejador {

    private int max = 0;
    private PopMessage m_PopMessage2;
    private SMTPMessage m_SMTPMessage2;

    private String g_metodo = "";
    private String g_origen = "";

    public Manejador() {
        m_PopMessage2 = new PopMessage();
        max = m_PopMessage2.getSize();
        m_PopMessage2.cerrar();
    }

    public void leer() {
        m_PopMessage2 = new PopMessage();
        if (m_PopMessage2.getSize() > max) {
            max++;
            //analizarLineas(m_PopMessage2.getMessageArray(max));
            boolean estado = analizarLineasSi(m_PopMessage2.getMessageArray(max));
            if (estado) {
                enviar_respuesta_(true);
            } else {
                enviar_respuesta_(false);
            }
        }
        m_PopMessage2.cerrar();
    }

    public void analizarLineas(List<String> messageArray) {
        String lineaMetodo = "";
        String lineaUsuario = "";
        int i = 0;
        for (String line : messageArray) {
            //System.out.println(line.toString());
            if (line.contains("Return-Path:")) {
                lineaUsuario = line;
            }
            if (line.contains("Subject:") || line.contains("subject:")) {
                if (line.regionMatches(0, "Subject:", 0, 8) || line.regionMatches(0, "subject:", 0, 8)) {
                    while (!messageArray.get(i).contains("]")) {
                        lineaMetodo = lineaMetodo + messageArray.get(i);
                        i++;
                    }
                    lineaMetodo = lineaMetodo + messageArray.get(i);
                }
            }
            i++;
        }
        System.out.println("linea encontrada=>" + lineaMetodo);
        //i++;

        //obtener mail usuario
        String mailFrom = getCorreoEmisor(lineaUsuario);
        System.out.println(mailFrom);

        //obtener metodo
        //posisiones para metodo y parametros
        int posIni = lineaMetodo.indexOf("[");
        int posFin = lineaMetodo.indexOf("]");
        String metodo = getMetodo(lineaMetodo, posIni);
        System.out.println("metodo-" + metodo);
        //obtener parametros        
        String[] parametros = getParametros(lineaMetodo, posIni, posFin);
        System.out.println(parametros.toString());
        ejecutarMetodos(metodo, parametros, mailFrom);

    }

    private String getMetodo(String lineaMetodo, int posIni) {
        //obtener metodo
        String metodo = lineaMetodo.substring(8, posIni).trim();
        metodo = metodo.toUpperCase();
        if (metodo.length() == 0) {
            metodo = "COMANDOS";
        }
        return metodo;
    }

    private String[] getParametros(String lineaMetodo, int posIni, int posFin) {
        String[] parametros = lineaMetodo.substring(posIni + 1, posFin).split(",");
        return parametros;
    }

    private String getCorreoEmisor(String lineaUsuario) {
        //posiciones para usuario mail
        int posIni1 = lineaUsuario.indexOf("<");
        int posFin1 = lineaUsuario.indexOf(">");
        return lineaUsuario.substring(posIni1 + 1, posFin1);
    }

    private void enviarMensajeCorreoOrigen(String prt_mailFrom, String prt_asunto, String prt_mensaje) {
        m_SMTPMessage2 = new SMTPMessage();
        //m_SMTPMessage2.sendMessage("grupo05sc@virtual.fcet.uagrm.edu.bo", prt_mailFrom, prt_asunto, prt_mensaje);
        m_SMTPMessage2.sendMessage("grupo05sc@tecnoweb.org.bo", prt_mailFrom, prt_asunto, prt_mensaje);
        m_SMTPMessage2.cerrar();
    }

    private boolean analizarLineasSi(List<String> messageArray) {
        g_origen = "";
        g_metodo = "";

        String lineaMetodo = "";
        String lineaUsuario = "";
        int i = 0;
        for (String line : messageArray) {
            //System.out.println(line.toString());
            if (line.contains("Return-Path:")) {
                lineaUsuario = line;
                //guardar linea correo origen
                g_origen = lineaUsuario;
            }
            if (line.contains("Subject:") || line.contains("subject:")) {
                if (line.regionMatches(0, "Subject:", 0, 8) || line.regionMatches(0, "subject:", 0, 8)) {
                    while (!messageArray.get(i).contains("]")) {
                        lineaMetodo = lineaMetodo + messageArray.get(i);
                        i++;
                    }

                    lineaMetodo = lineaMetodo + messageArray.get(i);
                    //guardar linea metodo globa;

                    g_metodo = lineaMetodo;
                    return true;
                }
            }
            i++;
        }
        return false;
    }

    private void enviar_respuesta_(boolean b) {
        if (b) {
            String mailFrom = getCorreoEmisor(g_origen);

            int posIni = g_metodo.indexOf("[");
            int posFin = g_metodo.indexOf("]");
            String metodo = getMetodo(g_metodo, posIni);

            String[] parametros = getParametros(g_metodo, posIni, posFin);

            ejecutarMetodos(metodo, parametros, mailFrom);
        } else {
            System.out.println("lo siento no se pudo mandar no se encontro el metodo.. \r\n");
        }
    }

    private void ejecutarMetodos(String prt_asunto, String[] prt_parametros, String prt_mailFrom) {
        String mensaje = "";
        System.out.println(prt_asunto + "...\r\n");

        switch (prt_asunto) {
            //domi CASO DE USO #1
            case "LISTUSUARIO": //metodo para listar clientes
                System.out.println(prt_asunto + "...\r\n");
                NUser m_NUsuario1 = new NUser();
                String s_resUsuario1 = m_NUsuario1.listUsuario(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(s_resUsuario1));
                break;

            default:
                //******************************//
                //***La cantidad de caracteres deberia ser 13 para el REGESTUDIANTE***/////
                //******************************//

                String mensaje5 = "<div class='error'><strong>ERROR!!! </strong><p class='texto-error'>en la instruccion porfavor revisa  al enviado COMANDOS[]; de la aplicacion</p></div>";
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(mensaje5));
                break;

        }
    }

    //******************************//
    //***ESTILO PARA LA VISTA***/////
    //******************************//
    public String getMensajeAyuda() {

        String estilo = "<link rel='stylesheet' href='https://codepen.io/ingyas/pen/NENBOm.css'>";
        String titulo = "<div>\n"
                + " <h2>Comandos de la aplicacion \"RESTAURANT LUIGGI\"</h2>\n"
                + "</div>";
        String ayudaClientes = "<div class=\"box\">\n"
                + "<div class=\"box-title\">\n"
                + "<h3>COMANDOS DE USUARIO</h3>\n"
                + "</div>\n"
                + "<strong>LISTAR USUARIO :</strong>\n"
                + "<p>listar  los USUARIO (0=todos ; 1=user clientes; 2=user proveedor; 3=usur Admin;4=user empleado  ):</p>\n"
                + "<p>ejemplo: ListUsuario[0]</p>\n"
                + "<p>ejemplo: ListUsuario[1]</p>\n"
                + "<div class=\"box-title\">\n"
                + "<h3>COMANDOS DE CLIENTES</h3>\n"
                + "</div>\n"
                + "<strong>LISTAR CLIENTES :</strong>\n"
                + "<p>listar todo los CLIENTES = 0</p>\n"
                + "<p>listar un cliente en especifico = 1</p>\n"
                + "<p>ListClientes[id o 0]</p>\n"
                + "<p>ejemplo: ListClientes[0]</p>\n"
                + "<strong>REGISTRAR CLIENTES :</strong> \n"
                + "<p>RegClientes[nombres, apellidos, direccion, email, telefono];</p>\n"
                + "<strong>EJEPLO DE REGISTRAR CLIENTE :</strong> \n"
                + "<p>RegClientes[Eugenio,Ruiz Castaeda,Av. Santos Dumont/ B. 5 de Octubre Nro. 512 ,eugenio@hotmail.com,7854554];</p>\n"
                + "<strong>EDITAR CLIENTE :</strong>\n"
                + "<p>EditCliente[1,nombres,apellidos, direccion, email, telefono];</p> \n"
                + "<strong>EJEPLO DE EDITAR CLIENTE :</strong> \n"
                + "<p>EditCliente[1,Cristian,Caballero,Los tajibos, crist@gmail.com, 67789013];</p> \n"
                + " <strong>ELIMINAR CLIENTE :</strong>\n"
                + " <p>DelCliente[idCliente];</p>\n"
                + " <strong>EJEMPLO DE ELIMINAR CLIENTE :</strong>\n"
                + " <p>DelCliente[1];</p>\n"
                + //PROVEEDOR
                "<strong>LISTAR PROVEEDORES :</strong>\n"
                + "<p>ejemplo LISTPROVEEDOR[0]</p>\n"
                + "<strong>CREAR PROVEEDOR :</strong>\n"
                + //[nombres, apellidos, direccion, email, telefono, nombreempresa]
                "<p>ejemplo REGPROVEEDOR[nombre,apellido,direccion,email,celular,empresa]</p>\n"
                + "<p>ejemplo REGPROVEEDOR[Lucia,Mendez,Av Pirai,lucia@hotmail.com,7854856,Lenovo]</p>\n"
                + "<strong>EDITAR PROVEEDOR :</strong>\n"
                + "<p>ejemplo EDIPROVEEDOR[idproveedor,direccion,empresa]</p>\n"
                + "<p>ejemplo EDIPROVEEDOR[2,Plan3000,Chimbol]</p>\n"
                + "<strong>ELIMINAR PROVEEDOR :</strong>\n"
                + "<p>ejemplo DELPROVEEDOR[idproveedor]</p>\n"
                + "<p>ejemplo DELPROVEEDOR[2]</p>\n"
                + //ALMACEN
                "<strong>LISTAR ALMACENES :</strong>\n"
                + "<p>ejemplo LISTALMACENES[0]</p>\n"
                + "<strong>REGISTRAR ALMACEN :</strong>\n"
                + "<p>ejemplo REGALMACEN[nombre]</p>\n"
                + "<p>ejemplo REGALMACEN[Almacen 1]</p>\n"
                + "<strong>EDITAR ALMACEN :</strong>\n"
                + "<p>ejemplo EDITALMACEN[idalmacen,nombre]</p>\n"
                + "<p>ejemplo EDITALMACEN[1,Almacen 2]</p>\n"
                + "<strong>ELIMINAR ALMACEN :</strong>\n"
                + "<p>ejemplo DELALMACEN[idalmacen]</p>\n"
                + "<p>ejemplo DELALMACEN[1]</p>\n"
                + //ESTADISTICA
                "<strong>ESTADISTICA: Cantidad de pedidos de todos los clientes por mes</strong>\n"
                + "<p>ejemplo ESTADISTICA[0]</p>\n"
                + //REPORTE
                "<strong>REPORTE : Reporte de clientes de una fecha a otra fecha</strong>\n"
                + "<p>ejemplo REPORTECLI[fechaInicio (Año-Mes-Dia),fechaFin (Año-Mes-Dia)]</p>\n"
                + "<p>ejemplo REPORTECLI[2020-03-10,2020-08-30]</p>\n"
                + " \n"
                + "  <div class=\"box-title\">\n"
                + "    <h4>RECOMENDACION:</h4>\n"
                + "    <p class=\"recomendacion\"><strong>Listar =></strong> si introduces 0 se listara a todos los Clientes.\n"
                + "    </p>\n"
                + "  </div>\n"
                + "</div>";
        //solo es un ejemplo para la ayuda: ayudaPedido_de_Clientes aun no hize el html el de clientes si ya esta terminado
        String ayudaPedido_de_Clientes = "<div class=\"box\">\n"
                + "<div class=\"box-title\">\n"
                + "<h3>COMANDOS DE PEDIDOS DE CLIENTES</h3>\n"
                + "</div>\n"
                + "<strong>LISTAR PEDIDO DE CLIENTES :</strong>\n"
                + "<p>listar todo los PEDIDOS = 0</p>\n"
                + "<p>listar un pedido en especifico = 1</p>\n"
                + "<p>ListPedidoCliente[id o 0]</p>\n"
                + "<p>ejemplo: ListPedidoCliente[0]</p>\n"
                + "<strong>REGISTRAR PEDIDOS :</strong> \n"
                + "<p>regNuevoPedido[codCliente,codUserempleado];</p>\n"
                + "<strong>EJEMPLO DE REGISTRAR PEDIDOS:</strong> \n"
                + "<p>regNuevoPedido[13,2];</p>\n"
                + "<strong>EJEMPLO DE ADD DETALLE PEDIDO :</strong> \n"
                + "<p>addADetallePedido[idpedidocliente, idproductoterminado, cantidad, idtipopedido];</p>\n"
                + "<p>addADetallePedido[6,1,1,1];</p>\n"
                + "<p>addADetallePedido[6,20,1,1];</p>\n"
                + "<strong>EDITAR PEDIDO DE CLIENTE :</strong>\n"
                + "<p>editPedidoCliente[idpedido,codCliente,codempleado];</p> \n"
                + "<strong>EJEMPLO DE EDITAR PROMOCION :</strong> \n"
                + "<p>editPedidoCliente[3,5,1];</p> \n"
                + "<strong>EJEMPLO DE EDITAR DETALLE DE PEDIDO CLIENTE:</strong> \n"
                + "<p>editDetallePedido[iddetallepedidocliente,cantidad,idtipopedido];</p> \n"
                + "<p>editDetallePedido[4,2,2];</p> \n"
                + " <strong>ELIMINAR DETALLE DE PEDIDO:</strong>\n"
                + " <p>delDetallePedido[iddetallepedido];</p>\n"
                + " <strong>EJEMPLO DE ELIMINAR DETALLE DE PEDIDO :</strong>\n"
                + " <p>delDetallePedido[1];</p>\n"
                + " <strong>ELIMINAR PEDIDO CLIENTE :</strong>\n"
                + " <p>delPedidoCliente[idPedidoCliente];</p>\n"
                + " <strong>EJEMPLO DE ELIMINAR  PROMOCION :</strong>\n"
                + " <p>delPedidoCliente[1];</p>\n"
                + " \n"
                + "  <div class=\"box-title\">\n"
                + "    <h4>RECOMENDACION:</h4>\n"
                + "    <p class=\"recomendacion\"><strong>Listar =></strong> si introduces 0 se listara a todos los Clientes.\n"
                + "    </p>\n"
                + "  </div>\n"
                + "</div>";
        String ayudaPromociones = "<div class=\"box\">\n"
                + "<div class=\"box-title\">\n"
                + "<h3>COMANDOS DE PROMOCIONES</h3>\n"
                + "</div>\n"
                + "<strong>LISTAR LAS PROMOCIONES :</strong>\n"
                + "<p>listar  Las PROMOCIONES = 0</p>\n"
                + "<p>ejemplo: ListPromocion[0]</p>\n"
                + "<strong>VER EL DETALLE DE UNA PROMOCION EN ESPECIFICO :</strong>\n"
                + "<p>ejemplo: ListDetallePromocion[2]</p>\n"
                + "<strong>REGISTRO DE PROMOCIONES :</strong> \n"
                + "<p>RegNuevaPromocion[nombrePromocion, precioPromocion, descripcion];</p>\n"
                + "<strong>EJEMPLO DE REGISTRAR NUEVA PROMOCION :</strong> \n"
                + "<p>RegNuevaPromocion[Promo Bolivia,50.00,'Dia de La Patria'];</p>\n"
                + "<strong>EJEMPLO DE ADD PRODUCTOS A LA PROMOCION :</strong> \n"
                + "<p>addProductPromo[Promocion, ProductoTerminado, cantidad];</p>\n"
                + "<p>addProductPromo['Promo Bolivia','Coca Cola 1/2',2];</p>\n"
                + "<p>addProductPromo['Promo Bolivia','Filete De Pollo Con Champiñone',2];</p>\n"
                + "<strong>EDITAR PROMOCIONES :</strong>\n"
                + "<p>EditPromocion[idpromocion,nombrepromocion,precio, descripcion];</p> \n"
                + "<strong>EJEMPLO DE EDITAR PROMOCION :</strong> \n"
                + "<p>EditPromocion[12,Promo el Gran Trabajador,60.00,'Dia Trabajador'];</p> \n"
                + "<strong>EJEMPLO DE EDITAR DETALLE DE PROMOCION :</strong> \n"
                + "<p>EditDetallePromo[idcantidadpromo,idPromocion,idProducto,cantidad];</p> \n"
                + "<p>EditDetallePromo[6,'Promo el Gran Trabajador','Hamburgueza Completa',2];</p> \n"
                + " <strong>ELIMINAR DETALLE DE PROMOCION :</strong>\n"
                + " <p>delDetallePromo[idcantidadpromo];</p>\n"
                + " <strong>EJEMPLO DE ELIMINAR DETALLE DE PROMO :</strong>\n"
                + " <p>delDetallePromo[6];</p>\n"
                + " <strong>ELIMINAR PROMOCION :</strong>\n"
                + " <p>delPromocion[idpromocion];</p>\n"
                + " <strong>EJEMPLO DE ELIMINAR  PROMOCION :</strong>\n"
                + " <p>delPromocion[1];</p>\n"
                + " \n"
                + "  <div class=\"box-title\">\n"
                + "    <h4>RECOMENDACION:</h4>\n"
                + "    <p class=\"recomendacion\"><strong>Listar =></strong> si introduces 0 se listara a todas las promociones.\n"
                + "    </p>\n"
                + "  </div>\n"
                + "</div>";

        String ayudaFactura = "<div class=\"box\">\n"
                + "<div class=\"box-title\">\n"
                + "<h3>COMANDOS DE FACTURA</h3>\n"
                + "</div>\n"
                + "<strong>LISTAR FACTURAS :</strong>\n"
                + "<p>listar todo las FACTURAS = 0</p>\n"
                + "<p>listar una FACTURA en especifico = 1</p>\n"
                + "<p>ListFactura[id o 0]</p>\n"
                + "<p>ejemplo: ListFactura[0]</p>\n"
                + "<strong>REGISTRAR FACTURA :</strong> \n"
                + "<p>RegFACTURA[NIT,nombreNIT,fecha,totalImporte, valorRecibido,Cambio,+\n"
                + "idUsuarioEmpleado,idSucursal, del_estado,nombrecliente];</p>\n"
                + "<strong>EJEPLO DE REGISTRAR FACTURA :</strong> \n"
                + "<p>RegFactura[7859,Jaque,07/08/2020,150,200,50,Laura,moscu,Eugenio];</p>\n"
                + "<strong>EDITAR FACTURA :</strong>\n"
                + "<p>EditFACTURA[idFactura, NIT , nombreNIT, fecha, totalImporte, valorRecibido,Cambio,+\n"
                + "tipoPago,idUsuarioEmpleado,idSucursal, del_estado];</p>\n"
                + "<strong>EJEPLO DE EDITAR FACTURA :</strong> \n"
                + "<p>EditFactura[9,7859,Alondra,07/08/2020,150,200,50,Laura,moscu,Eugenio,A o P];</p> \n"
                + " <strong>ELIMINAR FACTURA :</strong>\n"
                + " <p>DelFACTURA[idFactura];</p>\n"
                + " <strong>EJEMPLO DE ELIMINAR FACTURA:</strong>\n"
                + " <p>DelFactura[1];</p>\n"
                + " \n"
                + "  <div class=\"box-title\">\n"
                + "    <h4>RECOMENDACION:</h4>\n"
                + "    <p class=\"recomendacion\"><strong>Listar =></strong> si introduces 0 se listara a todas las facturas.\n"
                + "    </p>\n"
                + "  </div>\n"
                + "</div>";

        String ayudaProductoTerminado = "<div class=\"box\">\n"
                + "<div class=\"box-title\">\n"
                + "<h3>COMANDOS DE PRODUCTO TERMINADO</h3>\n"
                + "</div>\n"
                + "<strong>LISTAR PRODUCTO TERMINADO :</strong>\n"
                + "<p>listar todo los PRODUCTOS TERMINADOS = 0</p>\n"
                + "<p>listar un producto terminado en especifico = 1</p>\n"
                + "<p>ListProductoTerminado[id o 0]</p>\n"
                + "<p>ejemplo: ListProductoTerminado[0]</p>\n"
                + "<strong>REGISTRAR PRODUCTO TERMINADO :</strong> \n"
                + "<p>RegProductoTerminado[nombres, precioUnitario, del_estado];</p>\n"
                + "<strong>EJEPLO DE REGISTRAR PRODUCTO TERMINADO :</strong> \n"
                + "<p>RegProductoTerminado[Pizza, 45.05];</p>\n"
                + "<strong>REGISTRAR INSUMOD DEL PRODUCTO TERMINADO :</strong> \n"
                + "<p>RegInsumosProd[nombreProducto,nombreInsumo, precioUnitario];</p>\n"
                + "<strong>EJEPLO DE REGISTRAR INSUMOS PARA OBTENER EL PRODUCTO TERMINADO :</strong> \n"
                + "<p>RegInsumosProducto[pizza, chorizo, 20.05 ];</p>\n"
                + "<strong>EDITAR PRODUCTO TERMINADO :</strong>\n"
                + "<p>EditProductoTerminado[idProducto, nombres, precioUnitario, del_estado];</p> \n"
                + "<strong>EJEPLO DE EDITAR PRODUCTO TERMINADO :</strong> \n"
                + "<p>EditProductoTerminado[Salchipapa,15,A O E];</p> \n"
                + "<strong>EDITAR ISUMOS DEL PRODUCTO TERMINADO :</strong>\n"
                + "<p>EditInsumoProducto[nombreProducto,nombreInsumo,nombreInsumoActual,cantidadinsumos,del_estado];</p> \n"
                + "<strong>EJEPLO DE PRODUCTO TERMINADO :</strong> \n"
                + "<p>EditInsumoProducto[Pollo A La Broasted,Limon,Arroz,1,A O P];</p> \n"
                + " <strong>ELIMINAR PRODUCTO TERMINADO :</strong>\n"
                + " <p>DelProductoTerminado[idProducto];</p>\n"
                + " <strong>EJEMPLO DE ELIMINAR PRODUCTO TERMINADO :</strong>\n"
                + " <p>DelProductoTerminado[1];</p>\n"
                + " \n"
                + "  <div class=\"box-title\">\n"
                + "    <h4>RECOMENDACION:</h4>\n"
                + "    <p class=\"recomendacion\"><strong>Listar =></strong> si introduces 0 se listara a todos los Clientes.\n"
                + "    </p>\n"
                + "  </div>\n"
                + "</div>";

        return "Content-Type:text/html;\r\n<html>"
                + estilo
                + titulo
                + ayudaClientes
                + ayudaPedido_de_Clientes
                + ayudaPromociones
                + ayudaFactura
                + ayudaProductoTerminado
                + "</html>";

    }

    public String getMensajeTabla(String res) {
        String estilo = "<link rel='stylesheet' href='https://codepen.io/ingyas/pen/NENBOm.css'>";
        return "Content-Type:text/html;\r\n<html>" + estilo + res + "</html>";

    }

    public String getMensajeRespuesta(String res) {

        String estilo = "<link rel='stylesheet' href='https://codepen.io/ingyas/pen/NENBOm.css'>";
        return "Content-Type:text/html;\r\n<html>" + estilo + res + "</html>";
    }

}
