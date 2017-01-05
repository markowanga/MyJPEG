using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace JavaTest
{
    public partial class Form1 : Form
    {

        private bool initialized = false;
        public Form1()
        {
            InitializeComponent();
            comboBoxBoth.SelectedIndex = 0;
            comboBoxComp.SelectedIndex = 0;
            comboBoxDecomp.SelectedIndex = 0;
            labelStatusBoth.Hide();
            labelStatusDecomp.Hide();
            labelStausComp.Hide();
            LoadJavaExe();
        }


        //Creates or overrides java.exe file in bin/Debug
        //In case of failure message of jdk-path-change neccessity
        private void LoadJavaExe()
        {
            try
            {   // Open the text file using a stream reader.
                using (StreamReader sr = new StreamReader("astartdata.txt"))
                {
                    String jdkPath = sr.ReadToEnd();
                    try
                    {
                        File.Copy(jdkPath + @"\bin\java.exe", "java.exe", true);
                        initialized = true;
                    }
                    catch (FileNotFoundException e)
                    {
                        initialized = false;
                        MessageBox.Show("Nie zainicjowana lub ścieżka JDK");
                    }
                }

            }
            catch (Exception e)
            {
                MessageBox.Show("Problem przy wczytaniu sciezki do JDK");
                initialized = false;
            }
        }
        /*
         * Runs compresion and decompression with arguments read previously read form FileExplorer
         */
        private void buttonRun_Click(object sender, EventArgs e)
        {
            if (initialized)
            {
                string inName = labelInNameBoth.Text;
                string outName = textBoxOutNameBoth.Text;
                string compressed = textBoxCompressedNameBoth.Text;

                if (labelInNameBoth.Text == "" || textBoxCompressedNameBoth.Text == "" || textBoxOutNameBoth.Text == "")
                {
                    MessageBox.Show("Puste pola!");
                }
                else
                {
                    if (checkCompressedFileName(compressed) && checkInputOutputFileName(outName))
                    {
                        //Change status info
                        HideStatus(labelStatusBoth);
                        ///
                        System.Diagnostics.Process JavaTest = new System.Diagnostics.Process();
                        JavaTest.StartInfo.FileName = "java.exe";
                        JavaTest.StartInfo.Arguments = "Test " + inName + " " + compressed + " " + outName;
                        JavaTest.StartInfo.UseShellExecute = false;
                        JavaTest.StartInfo.CreateNoWindow = true;
                        // JavaTest.StartInfo.RedirectStandardOutput = true;
                        labelStatusBoth.Show();
                        JavaTest.Start();
                        JavaTest.WaitForExit();
                        labelStatusBoth.ForeColor = Color.Green;
                        labelStatusBoth.Text = "DONE - WSPÓŁCZYNNIK KOMPRESJI: " + Math.Round(CountCompressionRate(new FileInfo(inName), new FileInfo(compressed)),2);
                    }
                    else
                    {
                        MessageBox.Show("Niepoprawne rozszerzenia plików!!!");
                    }
                }
            }
        }

        private double CountCompressionRate(FileInfo inFile, FileInfo fileCompressed)
        {
            return (double)(inFile.Length) / fileCompressed.Length;
        }

        private bool checkFileName(string fileName, string format)
        {
            bool result = false;
            if (fileName.Length > 4)
            {
                result = fileName.Substring(fileName.Length - format.Length, format.Length) == format;
            }
            return result;
        }

        private bool checkCompressedFileName(string fileName)
        {
            return checkFileName(fileName, ".dmjpg");
        }
        private bool checkInputOutputFileName(string fileName)
        {
            return checkFileName(fileName, ".bmp");
        }

        private void buttonReadFileBoth_Click(object sender, EventArgs e)
        {
            if (initialized)
            {
                HideStatus(labelStatusBoth);
                openFileDialogBoth.ShowDialog();
                string fileName = openFileDialogBoth.FileName;
                if (checkInputOutputFileName(fileName))
                {
                    labelInNameBoth.Text = fileName;
                    string compressed = fileName.Insert(fileName.Length - 4, "-compressed.dmjpg");
                    compressed = compressed.Remove(compressed.Length - 4, 4);
                    string output = fileName.Insert(fileName.Length - 4, "-decompressed");
                    textBoxCompressedNameBoth.Text = compressed;
                    textBoxOutNameBoth.Text = output;
                }
                else
                {
                    MessageBox.Show("Wybrano plik o niepoprawnym rozszerzeniu");
                }
            }
        }

        private void HideStatus(Label labelStatus)
        {
            labelStatus.Hide();
            labelStatus.ForeColor = Color.Red;
            labelStatus.Text = "In progress...";
        }
        private void buttonReadInComp_Click(object sender, EventArgs e)
        {
            if (initialized)
            {
                HideStatus(labelStausComp);
                openFileDialogBoth.ShowDialog();
                string fileName = openFileDialogBoth.FileName;
                if (checkInputOutputFileName(fileName))
                {
                    labelFileNameComp.Text = fileName;
                    string compressed = fileName.Insert(fileName.Length - 4, "-compressed.dmjpg");
                    compressed = compressed.Remove(compressed.Length - 4, 4);
                    textBoxCompressedNameComp.Text = compressed;
                }
                else
                {
                    MessageBox.Show("Wybrano plik o niepoprawnym rozszerzeniu");
                }
            }
        }

        private void buttonRunComp_Click(object sender, EventArgs e)
        {
            if (initialized)
            {
                string inName = labelFileNameComp.Text;
                string compressed = textBoxCompressedNameComp.Text;

                if (labelFileNameComp.Text == "" || textBoxCompressedNameComp.Text == "")
                {
                    MessageBox.Show("Puste pola!");
                }
                else
                {
                    if (checkCompressedFileName(compressed) && checkInputOutputFileName(inName))
                    {
                        //Change status info
                        HideStatus(labelStausComp);

                        /*

                        ///
                        System.Diagnostics.Process JavaTest = new System.Diagnostics.Process();
                        JavaTest.StartInfo.FileName = "java.exe";
                        JavaTest.StartInfo.Arguments = "Test " + inName + " " + compressed + " " + outName;
                        JavaTest.StartInfo.UseShellExecute = false;
                        JavaTest.StartInfo.CreateNoWindow = true;
                        // JavaTest.StartInfo.RedirectStandardOutput = true;
                        */
                        labelStausComp.Show();
                        /*
                        JavaTest.Start();
                        JavaTest.WaitForExit();
                        */
                        labelStausComp.ForeColor = Color.Green;
                        labelStausComp.Text = "DONE - WSPÓŁCZYNNIK KOMPRESJI: " + Math.Round(CountCompressionRate(new FileInfo(inName), new FileInfo(compressed)), 2);
                    }
                    else
                    {
                        MessageBox.Show("Niepoprawne rozszerzenia plików!!!");
                    }
                }
            }
        }

        private void buttonReadInDecomp_Click(object sender, EventArgs e)
        {
            if (initialized)
            {
                HideStatus(labelStatusDecomp);
                openFileDialogBoth.ShowDialog();
                string fileName = openFileDialogBoth.FileName;
                if (checkCompressedFileName(fileName))
                {
                    labelInFileNameDecomp.Text = fileName;
                    string decompressed = fileName.Insert(fileName.Length - 6, "-decompressed.bmp");
                    decompressed = decompressed.Remove(decompressed.Length - 6, 6);
                    textBoxOutNameDecomp.Text = decompressed;
                }
                else
                {
                    MessageBox.Show("Wybrano plik o niepoprawnym rozszerzeniu");
                }
            }
        }

        private void buttonRunDecomp_Click(object sender, EventArgs e)
        {
            if (initialized)
            {
                string inName = labelInFileNameDecomp.Text;
                string decompressed = textBoxOutNameDecomp.Text;

                if (inName == "" || decompressed == "")
                {
                    MessageBox.Show("Puste pola!");
                }
                else
                {
                    if (checkCompressedFileName(inName) && checkInputOutputFileName(decompressed))
                    {
                        //Change status info
                        HideStatus(labelStatusDecomp);

                        /*

                        ///
                        System.Diagnostics.Process JavaTest = new System.Diagnostics.Process();
                        JavaTest.StartInfo.FileName = "java.exe";
                        JavaTest.StartInfo.Arguments = "Test " + inName + " " + compressed + " " + outName;
                        JavaTest.StartInfo.UseShellExecute = false;
                        JavaTest.StartInfo.CreateNoWindow = true;
                        // JavaTest.StartInfo.RedirectStandardOutput = true;
                        */
                        labelStatusDecomp.Show();
                        /*
                        JavaTest.Start();
                        JavaTest.WaitForExit();
                        */
                        labelStatusDecomp.ForeColor = Color.Green;
                        labelStatusDecomp.Text = "DONE";
                    }
                    else
                    {
                        MessageBox.Show("Niepoprawne rozszerzenia plików!!!");
                    }
                }
            }
        }

        private void buttonReadJava_Click(object sender, EventArgs e)
        {
            folderBrowserDialog.ShowDialog();
            String jdkPath = folderBrowserDialog.SelectedPath;
            if (jdkPath == "")
            {
                MessageBox.Show("Ścieżka niepoprawna");
            }
            else
            {
                using (StreamWriter sr = new StreamWriter("astartdata.txt"))
                {
                    sr.Write(jdkPath);
                }
                LoadJavaExe();
            }
        }
    }
}
