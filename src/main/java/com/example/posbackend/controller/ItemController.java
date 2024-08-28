package com.example.posbackend.controller;

import com.example.posbackend.dao.CustomerDataImpl;
import com.example.posbackend.dao.ItemDataImpl;
import com.example.posbackend.dto.CustomerDTO;
import com.example.posbackend.dto.ItemDTO;
import com.example.posbackend.util.UtilProcess;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = "",loadOnStartup = 2)
public class ItemController extends HttpServlet {
    static Logger logger= LoggerFactory.getLogger(ItemController.class);
    private Connection connection;
    @Override
    public void init() throws ServletException {

        try {
            var ctx = new InitialContext();
            DataSource pool = (DataSource)ctx.lookup("java:comp/env/jdbc/storeRegistration");
            this.connection = pool.getConnection();
            System.out.println(connection+"connection ok");
        }catch (NamingException | SQLException e){
            logger.error("info failed message"+e.getMessage());
            e.printStackTrace();
        }
        logger.info("Initializing Item controller ");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("called doPost   item method ");
        //save student-------------
        if(!req.getContentType().toLowerCase().startsWith("application/json")||req.getContentType()==null){
            //send error---------------
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }
        try {
            Jsonb jsonb = JsonbBuilder.create();
            ItemDTO itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);//jsonb eken kranne json type data tika object ekath ekka bind karana eka..
//            itemDTO.setId(UtilProcess.generateID());
            System.out.println("dopost"+itemDTO);
            ItemDataImpl itemData = new ItemDataImpl();
           itemData.saveItem(itemDTO,connection);
        } catch (JsonbException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("ItemCode");
        ItemDataImpl itemData = new ItemDataImpl();
        try(var writer=resp.getWriter()){
           ItemDTO itemDTO= itemData.getItem(code, connection);
            System.out.println(itemDTO);
            resp.setContentType("application/json");
            Jsonb jsonb = JsonbBuilder.create();
            jsonb.toJson(itemDTO,writer);
        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ItemDataImpl itemData = new ItemDataImpl();
        try (var writer = resp.getWriter()){
            var code = req.getParameter("ItemCode");
            Jsonb jsonb = JsonbBuilder.create();
           ItemDTO update_item = jsonb.fromJson(req.getReader(), ItemDTO.class);
            if(itemData.updateItem(update_item,connection,code)){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                System.out.println("doput"+itemData);
            }else{
                writer.write("Update Failed");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }


        } catch (JsonbException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var code = req.getParameter("ItemCode");
        try (var writer = resp.getWriter()){
            ItemDataImpl itemData = new ItemDataImpl();
            if(itemData.deleteItem(code, connection)){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                writer.write("Delete Failed");
            }
        }
        catch (JsonbException e) {
            throw new RuntimeException(e);
        }
    }

}
