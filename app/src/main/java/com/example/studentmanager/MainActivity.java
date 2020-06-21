package com.example.studentmanager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.util.Log;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    StudentAdapter adapter;
    List<Student> students;
    ListView listStudent;
    DAOQuery daoQuery;
    Context activityContext;
    DatePickerDialog pickerDob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listStudent = findViewById(R.id.list_student);

        activityContext = this;

        students = new ArrayList<>();
        daoQuery = new DAOQuery(this);
        daoQuery.open();
        students = daoQuery.getAllStudents();

        adapter = new StudentAdapter(students);
        listStudent.setAdapter(adapter);

        listStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Student student = students.get(position);
                final Dialog showStudentDialog = new Dialog(activityContext);
                showStudentDialog.setTitle("Edit Student Information");
                showStudentDialog.setContentView(R.layout.add_student_dialog);
                showStudentDialog.show();

                final EditText editSid = showStudentDialog.findViewById(R.id.edit_sid);
                final EditText editSname = showStudentDialog.findViewById(R.id.edit_sname);
                final EditText editSdob = showStudentDialog.findViewById(R.id.edit_sdob);
                final EditText editSHome = showStudentDialog.findViewById(R.id.edit_shome);
                final EditText editSMail = showStudentDialog.findViewById(R.id.edit_semail);
                final Button btnEdit = showStudentDialog.findViewById(R.id.btn_add_student);
                btnEdit.setVisibility(View.GONE);
                final Button btnCancel = showStudentDialog.findViewById(R.id.btn_cancel_add);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showStudentDialog.dismiss();
                    }
                });
                editSid.setText(student.getSid() + "");
                editSname.setText(student.getName());
                editSdob.setText(student.getDob());
                editSHome.setText(student.getAddress());
                editSMail.setText(student.getEmail());

                editSid.setEnabled(false);
                editSname.setEnabled(false);
                editSdob.setEnabled(false);
                editSHome.setEnabled(false);
                editSMail.setEnabled(false);

                editSid.setTextColor(Color.BLACK);
                editSdob.setTextColor(Color.BLACK);
                editSname.setTextColor(Color.BLACK);
                editSdob.setTextColor(Color.BLACK);
                editSHome.setTextColor(Color.BLACK);
                editSMail.setTextColor(Color.BLACK);

            }
        });

        registerForContextMenu(listStudent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem menuItemSearch = menu.findItem(R.id.search_btn_in_menu);
        final SearchView searchView = (SearchView) menuItemSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                StudentAdapter filAdapter = new StudentAdapter(getKeyStudent(query));
                listStudent.setAdapter(filAdapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                StudentAdapter filAdapter = new StudentAdapter(getKeyStudent(newText));
                listStudent.setAdapter(filAdapter);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_btn_in_menu) {

            final Dialog addStudentDialog = new Dialog(activityContext);
            addStudentDialog.setTitle("Add Student");
            addStudentDialog.setContentView(R.layout.add_student_dialog);
            addStudentDialog.show();

            final EditText editSid = addStudentDialog.findViewById(R.id.edit_sid);
            final EditText editSname = addStudentDialog.findViewById(R.id.edit_sname);
            final EditText editSdob = addStudentDialog.findViewById(R.id.edit_sdob);
            final EditText editSHome = addStudentDialog.findViewById(R.id.edit_shome);
            final EditText editSMail = addStudentDialog.findViewById(R.id.edit_semail);
            final Button btnAdd = addStudentDialog.findViewById(R.id.btn_add_student);
            final Button btnCancel = addStudentDialog.findViewById(R.id.btn_cancel_add);
            editSdob.setInputType(InputType.TYPE_NULL);
            editSdob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        final Calendar cldr = Calendar.getInstance();
                        int day = cldr.get(Calendar.DAY_OF_MONTH);
                        int month = cldr.get(Calendar.MONTH);
                        int year = cldr.get(Calendar.YEAR);
                        // date picker dialog
                        pickerDob = new DatePickerDialog(activityContext,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        editSdob.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                    }
                                }, year, month, day);
                        pickerDob.show();
                    }
                }
            });
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!editSid.getText().equals("") && !editSname.getText().toString().equals("") && !editSdob.getText().toString().equals("")
                            && !editSMail.getText().toString().equals("") && !editSHome.getText().toString().equals("")) {
                        try {
                            Student student = new Student(Integer.parseInt(editSid.getText().toString()), editSname.getText().toString(),
                                    editSdob.getText().toString(), editSMail.getText().toString(), editSHome.getText().toString());
                            daoQuery.addStudent(student);
                            students.add(student);
                            adapter.notifyDataSetChanged();
                            addStudentDialog.dismiss();
                        } catch (Exception e) {
                            Log.v("TAG", e.getMessage());
                            Toast.makeText(activityContext, "Fill all fields", Toast.LENGTH_SHORT).show();
                        }
                    } else
                        Toast.makeText(activityContext, "Fill all fields", Toast.LENGTH_SHORT).show();
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addStudentDialog.dismiss();
                }
            });
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.list_student) {
            menu.add("Edit Information");
            menu.add("Delete Student");
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        final Student student;
        if (item.getTitle().equals("Edit Information")) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            student = students.get(info.position);

            final Dialog editStudentDialog = new Dialog(activityContext);
            editStudentDialog.setTitle("Edit Student Information");
            editStudentDialog.setContentView(R.layout.add_student_dialog);
            editStudentDialog.show();

            final EditText editSid = editStudentDialog.findViewById(R.id.edit_sid);
            final EditText editSname = editStudentDialog.findViewById(R.id.edit_sname);
            final EditText editSdob = editStudentDialog.findViewById(R.id.edit_sdob);
            final EditText editSHome = editStudentDialog.findViewById(R.id.edit_shome);
            final EditText editSMail = editStudentDialog.findViewById(R.id.edit_semail);
            final Button btnEdit = editStudentDialog.findViewById(R.id.btn_add_student);
            btnEdit.setText("Save");
            final Button btnCancel = editStudentDialog.findViewById(R.id.btn_cancel_add);

            editSid.setText(student.getSid() + "");
            editSname.setText(student.getName());
            editSdob.setText(student.getDob());
            editSHome.setText(student.getAddress());
            editSMail.setText(student.getEmail());


            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!editSid.getText().equals("") && !editSname.getText().toString().equals("") && !editSdob.getText().toString().equals("")
                            && !editSMail.getText().toString().equals("") && !editSHome.getText().toString().equals("")) {
                        try {
                            student.setSid(Integer.parseInt(editSid.getText().toString()));
                            student.setAddress(editSHome.getText().toString());
                            student.setName(editSname.getText().toString());
                            student.setDob(editSdob.getText().toString());
                            student.setEmail(editSMail.getText().toString());
                            daoQuery.updateStudent(student);
                            editStudentDialog.dismiss();
                        } catch (Exception e) {
                            Log.v("TAG", e.getMessage());
                            Toast.makeText(activityContext, "Fill all fields", Toast.LENGTH_SHORT).show();
                        }
                    } else
                        Toast.makeText(activityContext, "Fill all fields", Toast.LENGTH_SHORT).show();
                }
            });
            editSdob.setInputType(InputType.TYPE_NULL);
            editSdob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        final Calendar cldr = Calendar.getInstance();
                        int day = cldr.get(Calendar.DAY_OF_MONTH);
                        int month = cldr.get(Calendar.MONTH);
                        int year = cldr.get(Calendar.YEAR);
                        // date picker dialog
                        pickerDob = new DatePickerDialog(activityContext,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        editSdob.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                    }
                                }, year, month, day);
                        pickerDob.show();
                    }
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editStudentDialog.dismiss();
                }
            });
        } else {
            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            student = students.get(menuInfo.position);
            daoQuery.deleteStudent(student);
            students.remove(student);
            adapter.notifyDataSetChanged();
        }
        return true;
    }

    //Lấy danh sách student chứa key
    public List<Student> getKeyStudent(String fillText) {
        List<Student> fillItems = new ArrayList<>();
        fillText = fillText.toLowerCase();
        for (Student student : students) {
            if (student.getName().toLowerCase().contains(fillText)) fillItems.add(student);
            else if (String.valueOf(student.getSid()).toLowerCase().contains(fillText))
                fillItems.add(student);
        }
        return fillItems;
    }

}
