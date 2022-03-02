package com.qashar.mypersonalaccounting.Else;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.qashar.mypersonalaccounting.Models.Task;
import com.qashar.mypersonalaccounting.RoomDB.MyViewModels;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;



public class Print {
    private Context context;
    private Application application;
    private MyViewModels viewModels;
    private File pdfFile;;
    public Print(Context context,Application application) {
        this.context = context;
        this.application = application;
        viewModels = new MyViewModels(application);
    }
    public void getDataAndPrint(Long from , Long to){
        viewModels.getAllTasksByDate(from,to).observe((LifecycleOwner) context, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                try {
                    print(tasks);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void getDataAndPrint(String wallet){
        viewModels.getAllTasksByWallet(wallet).observe((LifecycleOwner) context, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                try {
                    print(tasks);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void getDataByWalletAndPrint(Long from , Long to,String wallet){
        viewModels.getAllTasksByDate(from,to).observe((LifecycleOwner) context, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                try {
                    print(tasks);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void getData(){
        viewModels.getAllTasks().observe((LifecycleOwner) context, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                try {
                    print(tasks);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void print(List<Task> tasks)
            throws IOException, DocumentException {
        if (tasks.size()==0){
            Toast.makeText(context, "No Data !", Toast.LENGTH_SHORT).show();
        }else {

            File docsFolder = new File(new Consents().MAINPATH);
            if (!docsFolder.exists()) {
                docsFolder.mkdir();
            }
            String pdfname = "invoice_" + new Settings(context).getInvoiceNum() + ".pdf";
            pdfFile = new File(docsFolder.getAbsolutePath(), pdfname);
            OutputStream output = new FileOutputStream(pdfFile);
            com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.A4);


            PdfPTable billTable = new PdfPTable(10); //one page contains 15 records

            billTable.setWidthPercentage(100);
//                    billTable.setWidths(new float[] {1,1,1,1,1,1,1,1,1,1,1});
            billTable.setSpacingBefore(30.0f);
            billTable.addCell(getBillHeaderCell("NOTE"));
            billTable.addCell(getBillHeaderCell("GROUP"));
            billTable.addCell(getBillHeaderCell("CONTACT"));
            billTable.addCell(getBillHeaderCell("TO"));
            billTable.addCell(getBillHeaderCell("WALLET"));
            billTable.addCell(getBillHeaderCell("CURRENCY"));
            billTable.addCell(getBillHeaderCell("COAST"));
            billTable.addCell(getBillHeaderCell("MAIN A"));
            billTable.addCell(getBillHeaderCell("MAIN "));
            billTable.addCell(getBillHeaderCell("DATE"));


            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                billTable.addCell(getBillBodyCell(task.getNote()));
                billTable.addCell(getBillBodyCell(task.getGroup()));
                billTable.addCell(getBillBodyCell(task.getContact()));
                billTable.addCell(getBillBodyCell("TO"));
                billTable.addCell(getBillBodyCell(task.getWallet()));
                billTable.addCell(getBillBodyCell(task.getCurrency()));
                billTable.addCell(getBillBodyCell(task.getPrice() + ""));
                billTable.addCell(getBillBodyCell(task.getEmoji()));
                billTable.addCell(getBillBodyCell(task.getType()));
                billTable.addCell(getBillBodyCell(task.getDate()));
            }


            PdfWriter.getInstance(document, output);
            document.open();
            document.add(billTable);
            new Settings(context).setInvoiceNum(new Settings(context).getInvoiceNum() + 1);
            Toast.makeText(context, "saved", Toast.LENGTH_SHORT).show();
            document.close();


        }

    }

    private PdfPCell getBillHeaderCell(String s) {
        FontSelector fs = new FontSelector();
        com.itextpdf.text.Font font = FontFactory.getFont(FontFactory.HELVETICA, 11);
        font.setColor(BaseColor.BLACK);
        fs.addFont(font);
        Phrase phrase = fs.process(s);
        PdfPCell cell = new PdfPCell (new Paragraph(phrase));
        cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
        cell.setBorderColor(BaseColor.LIGHT_GRAY);
        cell.setBackgroundColor(BaseColor.CYAN);
        cell.setPadding(10);
        return cell;
    }
    private PdfPCell getBillBodyCell(String s) {
        FontSelector fs = new FontSelector();
        com.itextpdf.text.Font font = FontFactory.getFont(FontFactory.HELVETICA, 11);
        font.setColor(BaseColor.WHITE);
        fs.addFont(font);
        Phrase phrase = fs.process(s);
        PdfPCell cell = new PdfPCell (new Paragraph(phrase));
        cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
        cell.setBorderColor(BaseColor.LIGHT_GRAY);
        cell.setBackgroundColor(BaseColor.GRAY);
        cell.setPadding(10);
        return cell;
    }
}
