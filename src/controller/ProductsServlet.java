package controller;

import model.Product;
import service.ProductService;
import service.ProductServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ProductsServlet", urlPatterns = "/products")
public class ProductsServlet extends HttpServlet {
    private ProductService productService = new ProductServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "insert":
                insertIntoDatabase(request, response);
                break;
            case "edit":
                editProduct(request, response);
                break;
            case "delete":
                deleteProduct(request, response);
                break;
            default:
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "insert":
                showInsertForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                showDeleteForm(request, response);
                break;
            default:
                showProduct(request, response);
                break;
        }
    }

    private void editProduct(HttpServletRequest request, HttpServletResponse response) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String code = request.getParameter("code");
            String name = request.getParameter("name");
            float price = Float.parseFloat(request.getParameter("price"));
            int category_id = Integer.parseInt(request.getParameter("category_id"));
            Product product = this.productService.findById(id);
            RequestDispatcher dispatcher;

            if (product == null) {
                dispatcher = request.getRequestDispatcher("error-404.jsp");
            } else {
                product.setCode(code);
                product.setName(name);
                product.setPrice(price);
                product.setCategory_id(category_id);
                this.productService.update(id, product);
                request.setAttribute("product", product);
                request.setAttribute("message", "Product information was updated");
                dispatcher = request.getRequestDispatcher("edit.jsp");
            }
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            Product product = this.productService.findById(id);
            RequestDispatcher dispatcher;
            if (product == null) {
                dispatcher = request.getRequestDispatcher("error-404.jsp");
            } else {
                request.setAttribute("product", product);
                dispatcher = request.getRequestDispatcher("edit.jsp");
            }
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            Product product = this.productService.findById(id);
            RequestDispatcher dispatcher;

            if (product == null) {
                dispatcher = request.getRequestDispatcher("error-404.jsp");
            } else {
                this.productService.remove(id);
                response.sendRedirect("/products");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showDeleteForm(HttpServletRequest request, HttpServletResponse response) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));

            Product product = this.productService.findById(id);
            RequestDispatcher dispatcher;

            if (product == null) {
                dispatcher = request.getRequestDispatcher("error-404.jsp");
            } else {
                request.setAttribute("product", product);
                dispatcher = request.getRequestDispatcher("delete.jsp");
            }
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void insertIntoDatabase(HttpServletRequest request, HttpServletResponse response) {
        try {
            String code = request.getParameter("code");
            String name = request.getParameter("name");
            float price = Float.parseFloat(request.getParameter("price"));
            int category_id = Integer.parseInt(request.getParameter("category_id"));

            Product product = new Product(code, name, price, category_id);
            RequestDispatcher dispatcher = request.getRequestDispatcher("insert.jsp");
            request.setAttribute("message", "New product was insert");

            this.productService.save(product);
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showInsertForm(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher dispatcher = request.getRequestDispatcher("insert.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showProduct(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Product> products = productService.findAll();
            request.setAttribute("products", products);
            RequestDispatcher dispatcher = request.getRequestDispatcher("products.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
