namespace JavaTest
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.openFileDialogBoth = new System.Windows.Forms.OpenFileDialog();
            this.buttonReadFileBoth = new System.Windows.Forms.Button();
            this.buttonReadJava = new System.Windows.Forms.Button();
            this.labelStausComp = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.comboBoxComp = new System.Windows.Forms.ComboBox();
            this.labelFileNameComp = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.textBoxCompressedNameComp = new System.Windows.Forms.TextBox();
            this.label6 = new System.Windows.Forms.Label();
            this.buttonReadInComp = new System.Windows.Forms.Button();
            this.buttonRunComp = new System.Windows.Forms.Button();
            this.labelStatusDecomp = new System.Windows.Forms.Label();
            this.label8 = new System.Windows.Forms.Label();
            this.comboBoxDecomp = new System.Windows.Forms.ComboBox();
            this.labelInFileNameDecomp = new System.Windows.Forms.Label();
            this.label11 = new System.Windows.Forms.Label();
            this.textBoxOutNameDecomp = new System.Windows.Forms.TextBox();
            this.label12 = new System.Windows.Forms.Label();
            this.buttonReadInDecomp = new System.Windows.Forms.Button();
            this.buttonRunDecomp = new System.Windows.Forms.Button();
            this.buttonRunBoth = new System.Windows.Forms.Button();
            this.labelFileNameBoth = new System.Windows.Forms.Label();
            this.textBoxCompressedNameBoth = new System.Windows.Forms.TextBox();
            this.textBoxOutNameBoth = new System.Windows.Forms.TextBox();
            this.labelTempNameBoth = new System.Windows.Forms.Label();
            this.labelOutNameBoth = new System.Windows.Forms.Label();
            this.labelInNameBoth = new System.Windows.Forms.Label();
            this.comboBoxBoth = new System.Windows.Forms.ComboBox();
            this.labelQantTableBoth = new System.Windows.Forms.Label();
            this.labelStatusBoth = new System.Windows.Forms.Label();
            this.folderBrowserDialog = new System.Windows.Forms.FolderBrowserDialog();
            this.panel1 = new System.Windows.Forms.Panel();
            this.panel2 = new System.Windows.Forms.Panel();
            this.panel3 = new System.Windows.Forms.Panel();
            this.panel1.SuspendLayout();
            this.panel2.SuspendLayout();
            this.panel3.SuspendLayout();
            this.SuspendLayout();
            // 
            // openFileDialogBoth
            // 
            this.openFileDialogBoth.FileName = "openFileDialog1";
            // 
            // buttonReadFileBoth
            // 
            this.buttonReadFileBoth.Location = new System.Drawing.Point(871, 28);
            this.buttonReadFileBoth.Name = "buttonReadFileBoth";
            this.buttonReadFileBoth.Size = new System.Drawing.Size(94, 30);
            this.buttonReadFileBoth.TabIndex = 1;
            this.buttonReadFileBoth.Text = "Wczytaj";
            this.buttonReadFileBoth.UseVisualStyleBackColor = true;
            this.buttonReadFileBoth.Click += new System.EventHandler(this.buttonReadFileBoth_Click);
            // 
            // buttonReadJava
            // 
            this.buttonReadJava.Location = new System.Drawing.Point(14, 809);
            this.buttonReadJava.Name = "buttonReadJava";
            this.buttonReadJava.Size = new System.Drawing.Size(222, 30);
            this.buttonReadJava.TabIndex = 10;
            this.buttonReadJava.Text = "Zmień domyślną lokalizację JDK";
            this.buttonReadJava.UseVisualStyleBackColor = true;
            this.buttonReadJava.Click += new System.EventHandler(this.buttonReadJava_Click);
            // 
            // labelStausComp
            // 
            this.labelStausComp.AutoSize = true;
            this.labelStausComp.Font = new System.Drawing.Font("Microsoft Sans Serif", 13.8F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.labelStausComp.ForeColor = System.Drawing.Color.Red;
            this.labelStausComp.Location = new System.Drawing.Point(9, 115);
            this.labelStausComp.Name = "labelStausComp";
            this.labelStausComp.Size = new System.Drawing.Size(166, 29);
            this.labelStausComp.TabIndex = 22;
            this.labelStausComp.Text = "In progress...";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(11, 86);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(140, 17);
            this.label2.TabIndex = 21;
            this.label2.Text = "Nr tabeli kwantyzacji:";
            // 
            // comboBoxComp
            // 
            this.comboBoxComp.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.comboBoxComp.FormattingEnabled = true;
            this.comboBoxComp.Items.AddRange(new object[] {
            "1",
            "2",
            "3",
            "4",
            "5"});
            this.comboBoxComp.Location = new System.Drawing.Point(224, 86);
            this.comboBoxComp.Name = "comboBoxComp";
            this.comboBoxComp.Size = new System.Drawing.Size(629, 24);
            this.comboBoxComp.TabIndex = 20;
            // 
            // labelFileNameComp
            // 
            this.labelFileNameComp.AutoSize = true;
            this.labelFileNameComp.Location = new System.Drawing.Point(221, 17);
            this.labelFileNameComp.Name = "labelFileNameComp";
            this.labelFileNameComp.Size = new System.Drawing.Size(103, 17);
            this.labelFileNameComp.TabIndex = 19;
            this.labelFileNameComp.Text = "Nie wczytano...";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(11, 48);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(198, 17);
            this.label5.TabIndex = 17;
            this.label5.Text = "Nazwa pliku skopresowanego:";
            // 
            // textBoxCompressedNameComp
            // 
            this.textBoxCompressedNameComp.Location = new System.Drawing.Point(224, 48);
            this.textBoxCompressedNameComp.Name = "textBoxCompressedNameComp";
            this.textBoxCompressedNameComp.Size = new System.Drawing.Size(629, 22);
            this.textBoxCompressedNameComp.TabIndex = 15;
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(11, 17);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(129, 17);
            this.label6.TabIndex = 14;
            this.label6.Text = "Scieżka do zdjęcia:";
            // 
            // buttonReadInComp
            // 
            this.buttonReadInComp.Location = new System.Drawing.Point(862, 10);
            this.buttonReadInComp.Name = "buttonReadInComp";
            this.buttonReadInComp.Size = new System.Drawing.Size(94, 30);
            this.buttonReadInComp.TabIndex = 13;
            this.buttonReadInComp.Text = "Wczytaj";
            this.buttonReadInComp.UseVisualStyleBackColor = true;
            this.buttonReadInComp.Click += new System.EventHandler(this.buttonReadInComp_Click);
            // 
            // buttonRunComp
            // 
            this.buttonRunComp.Location = new System.Drawing.Point(14, 156);
            this.buttonRunComp.Name = "buttonRunComp";
            this.buttonRunComp.Size = new System.Drawing.Size(843, 37);
            this.buttonRunComp.TabIndex = 12;
            this.buttonRunComp.Text = "COMPRESS";
            this.buttonRunComp.UseVisualStyleBackColor = true;
            this.buttonRunComp.Click += new System.EventHandler(this.buttonRunComp_Click);
            // 
            // labelStatusDecomp
            // 
            this.labelStatusDecomp.AutoSize = true;
            this.labelStatusDecomp.Font = new System.Drawing.Font("Microsoft Sans Serif", 13.8F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.labelStatusDecomp.ForeColor = System.Drawing.Color.Red;
            this.labelStatusDecomp.Location = new System.Drawing.Point(14, 135);
            this.labelStatusDecomp.Name = "labelStatusDecomp";
            this.labelStatusDecomp.Size = new System.Drawing.Size(166, 29);
            this.labelStatusDecomp.TabIndex = 33;
            this.labelStatusDecomp.Text = "In progress...";
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Location = new System.Drawing.Point(16, 106);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(140, 17);
            this.label8.TabIndex = 32;
            this.label8.Text = "Nr tabeli kwantyzacji:";
            // 
            // comboBoxDecomp
            // 
            this.comboBoxDecomp.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.comboBoxDecomp.FormattingEnabled = true;
            this.comboBoxDecomp.Items.AddRange(new object[] {
            "1",
            "2",
            "3",
            "4",
            "5"});
            this.comboBoxDecomp.Location = new System.Drawing.Point(229, 106);
            this.comboBoxDecomp.Name = "comboBoxDecomp";
            this.comboBoxDecomp.Size = new System.Drawing.Size(629, 24);
            this.comboBoxDecomp.TabIndex = 31;
            // 
            // labelInFileNameDecomp
            // 
            this.labelInFileNameDecomp.AutoSize = true;
            this.labelInFileNameDecomp.Location = new System.Drawing.Point(226, 37);
            this.labelInFileNameDecomp.Name = "labelInFileNameDecomp";
            this.labelInFileNameDecomp.Size = new System.Drawing.Size(103, 17);
            this.labelInFileNameDecomp.TabIndex = 30;
            this.labelInFileNameDecomp.Text = "Nie wczytano...";
            // 
            // label11
            // 
            this.label11.AutoSize = true;
            this.label11.Location = new System.Drawing.Point(16, 68);
            this.label11.Name = "label11";
            this.label11.Size = new System.Drawing.Size(198, 17);
            this.label11.TabIndex = 28;
            this.label11.Text = "Nazwa pliku skopresowanego:";
            // 
            // textBoxOutNameDecomp
            // 
            this.textBoxOutNameDecomp.Location = new System.Drawing.Point(229, 68);
            this.textBoxOutNameDecomp.Name = "textBoxOutNameDecomp";
            this.textBoxOutNameDecomp.Size = new System.Drawing.Size(629, 22);
            this.textBoxOutNameDecomp.TabIndex = 26;
            // 
            // label12
            // 
            this.label12.AutoSize = true;
            this.label12.Location = new System.Drawing.Point(16, 37);
            this.label12.Name = "label12";
            this.label12.Size = new System.Drawing.Size(129, 17);
            this.label12.TabIndex = 25;
            this.label12.Text = "Scieżka do zdjęcia:";
            // 
            // buttonReadInDecomp
            // 
            this.buttonReadInDecomp.Location = new System.Drawing.Point(863, 30);
            this.buttonReadInDecomp.Name = "buttonReadInDecomp";
            this.buttonReadInDecomp.Size = new System.Drawing.Size(94, 30);
            this.buttonReadInDecomp.TabIndex = 24;
            this.buttonReadInDecomp.Text = "Wczytaj";
            this.buttonReadInDecomp.UseVisualStyleBackColor = true;
            this.buttonReadInDecomp.Click += new System.EventHandler(this.buttonReadInDecomp_Click);
            // 
            // buttonRunDecomp
            // 
            this.buttonRunDecomp.Location = new System.Drawing.Point(19, 167);
            this.buttonRunDecomp.Name = "buttonRunDecomp";
            this.buttonRunDecomp.Size = new System.Drawing.Size(843, 37);
            this.buttonRunDecomp.TabIndex = 23;
            this.buttonRunDecomp.Text = "DECOMPRESS";
            this.buttonRunDecomp.UseVisualStyleBackColor = true;
            this.buttonRunDecomp.Click += new System.EventHandler(this.buttonRunDecomp_Click);
            // 
            // buttonRunBoth
            // 
            this.buttonRunBoth.Location = new System.Drawing.Point(27, 204);
            this.buttonRunBoth.Name = "buttonRunBoth";
            this.buttonRunBoth.Size = new System.Drawing.Size(843, 37);
            this.buttonRunBoth.TabIndex = 0;
            this.buttonRunBoth.Text = "COMPRESS AND DECOMPRESS";
            this.buttonRunBoth.UseVisualStyleBackColor = true;
            this.buttonRunBoth.Click += new System.EventHandler(this.buttonRun_Click);
            // 
            // labelFileNameBoth
            // 
            this.labelFileNameBoth.AutoSize = true;
            this.labelFileNameBoth.Location = new System.Drawing.Point(24, 35);
            this.labelFileNameBoth.Name = "labelFileNameBoth";
            this.labelFileNameBoth.Size = new System.Drawing.Size(129, 17);
            this.labelFileNameBoth.TabIndex = 2;
            this.labelFileNameBoth.Text = "Scieżka do zdjęcia:";
            // 
            // textBoxCompressedNameBoth
            // 
            this.textBoxCompressedNameBoth.Location = new System.Drawing.Point(237, 66);
            this.textBoxCompressedNameBoth.Name = "textBoxCompressedNameBoth";
            this.textBoxCompressedNameBoth.Size = new System.Drawing.Size(629, 22);
            this.textBoxCompressedNameBoth.TabIndex = 3;
            // 
            // textBoxOutNameBoth
            // 
            this.textBoxOutNameBoth.Location = new System.Drawing.Point(237, 99);
            this.textBoxOutNameBoth.Name = "textBoxOutNameBoth";
            this.textBoxOutNameBoth.Size = new System.Drawing.Size(629, 22);
            this.textBoxOutNameBoth.TabIndex = 4;
            // 
            // labelTempNameBoth
            // 
            this.labelTempNameBoth.AutoSize = true;
            this.labelTempNameBoth.Location = new System.Drawing.Point(24, 66);
            this.labelTempNameBoth.Name = "labelTempNameBoth";
            this.labelTempNameBoth.Size = new System.Drawing.Size(198, 17);
            this.labelTempNameBoth.TabIndex = 5;
            this.labelTempNameBoth.Text = "Nazwa pliku skopresowanego:";
            // 
            // labelOutNameBoth
            // 
            this.labelOutNameBoth.AutoSize = true;
            this.labelOutNameBoth.Location = new System.Drawing.Point(24, 102);
            this.labelOutNameBoth.Name = "labelOutNameBoth";
            this.labelOutNameBoth.Size = new System.Drawing.Size(154, 17);
            this.labelOutNameBoth.TabIndex = 6;
            this.labelOutNameBoth.Text = "Nazwa po dekompresji:";
            // 
            // labelInNameBoth
            // 
            this.labelInNameBoth.AutoSize = true;
            this.labelInNameBoth.Location = new System.Drawing.Point(234, 35);
            this.labelInNameBoth.Name = "labelInNameBoth";
            this.labelInNameBoth.Size = new System.Drawing.Size(103, 17);
            this.labelInNameBoth.TabIndex = 7;
            this.labelInNameBoth.Text = "Nie wczytano...";
            // 
            // comboBoxBoth
            // 
            this.comboBoxBoth.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.comboBoxBoth.FormattingEnabled = true;
            this.comboBoxBoth.Items.AddRange(new object[] {
            "1",
            "2",
            "3",
            "4",
            "5"});
            this.comboBoxBoth.Location = new System.Drawing.Point(237, 143);
            this.comboBoxBoth.Name = "comboBoxBoth";
            this.comboBoxBoth.Size = new System.Drawing.Size(629, 24);
            this.comboBoxBoth.TabIndex = 8;
            // 
            // labelQantTableBoth
            // 
            this.labelQantTableBoth.AutoSize = true;
            this.labelQantTableBoth.Location = new System.Drawing.Point(24, 143);
            this.labelQantTableBoth.Name = "labelQantTableBoth";
            this.labelQantTableBoth.Size = new System.Drawing.Size(140, 17);
            this.labelQantTableBoth.TabIndex = 9;
            this.labelQantTableBoth.Text = "Nr tabeli kwantyzacji:";
            // 
            // labelStatusBoth
            // 
            this.labelStatusBoth.AutoSize = true;
            this.labelStatusBoth.Font = new System.Drawing.Font("Microsoft Sans Serif", 13.8F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.labelStatusBoth.ForeColor = System.Drawing.Color.Red;
            this.labelStatusBoth.Location = new System.Drawing.Point(22, 172);
            this.labelStatusBoth.Name = "labelStatusBoth";
            this.labelStatusBoth.Size = new System.Drawing.Size(166, 29);
            this.labelStatusBoth.TabIndex = 11;
            this.labelStatusBoth.Text = "In progress...";
            // 
            // panel1
            // 
            this.panel1.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.panel1.Controls.Add(this.labelStatusBoth);
            this.panel1.Controls.Add(this.labelQantTableBoth);
            this.panel1.Controls.Add(this.comboBoxBoth);
            this.panel1.Controls.Add(this.labelInNameBoth);
            this.panel1.Controls.Add(this.labelOutNameBoth);
            this.panel1.Controls.Add(this.labelTempNameBoth);
            this.panel1.Controls.Add(this.textBoxOutNameBoth);
            this.panel1.Controls.Add(this.textBoxCompressedNameBoth);
            this.panel1.Controls.Add(this.labelFileNameBoth);
            this.panel1.Controls.Add(this.buttonReadFileBoth);
            this.panel1.Controls.Add(this.buttonRunBoth);
            this.panel1.Location = new System.Drawing.Point(12, 12);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(981, 287);
            this.panel1.TabIndex = 34;
            // 
            // panel2
            // 
            this.panel2.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.panel2.Controls.Add(this.labelStausComp);
            this.panel2.Controls.Add(this.label2);
            this.panel2.Controls.Add(this.comboBoxComp);
            this.panel2.Controls.Add(this.labelFileNameComp);
            this.panel2.Controls.Add(this.label5);
            this.panel2.Controls.Add(this.textBoxCompressedNameComp);
            this.panel2.Controls.Add(this.label6);
            this.panel2.Controls.Add(this.buttonReadInComp);
            this.panel2.Controls.Add(this.buttonRunComp);
            this.panel2.Location = new System.Drawing.Point(14, 319);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(978, 232);
            this.panel2.TabIndex = 35;
            // 
            // panel3
            // 
            this.panel3.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.panel3.Controls.Add(this.labelStatusDecomp);
            this.panel3.Controls.Add(this.label8);
            this.panel3.Controls.Add(this.comboBoxDecomp);
            this.panel3.Controls.Add(this.labelInFileNameDecomp);
            this.panel3.Controls.Add(this.label11);
            this.panel3.Controls.Add(this.textBoxOutNameDecomp);
            this.panel3.Controls.Add(this.label12);
            this.panel3.Controls.Add(this.buttonReadInDecomp);
            this.panel3.Controls.Add(this.buttonRunDecomp);
            this.panel3.Location = new System.Drawing.Point(13, 571);
            this.panel3.Name = "panel3";
            this.panel3.Size = new System.Drawing.Size(980, 232);
            this.panel3.TabIndex = 36;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1005, 845);
            this.Controls.Add(this.panel3);
            this.Controls.Add(this.panel2);
            this.Controls.Add(this.panel1);
            this.Controls.Add(this.buttonReadJava);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.Name = "Form1";
            this.Text = "DM-JPEGCompressor";
            this.panel1.ResumeLayout(false);
            this.panel1.PerformLayout();
            this.panel2.ResumeLayout(false);
            this.panel2.PerformLayout();
            this.panel3.ResumeLayout(false);
            this.panel3.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion
        private System.Windows.Forms.OpenFileDialog openFileDialogBoth;
        private System.Windows.Forms.Button buttonReadFileBoth;
        private System.Windows.Forms.Button buttonReadJava;
        private System.Windows.Forms.Label labelStausComp;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.ComboBox comboBoxComp;
        private System.Windows.Forms.Label labelFileNameComp;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.TextBox textBoxCompressedNameComp;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Button buttonReadInComp;
        private System.Windows.Forms.Button buttonRunComp;
        private System.Windows.Forms.Label labelStatusDecomp;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.ComboBox comboBoxDecomp;
        private System.Windows.Forms.Label labelInFileNameDecomp;
        private System.Windows.Forms.Label label11;
        private System.Windows.Forms.TextBox textBoxOutNameDecomp;
        private System.Windows.Forms.Label label12;
        private System.Windows.Forms.Button buttonReadInDecomp;
        private System.Windows.Forms.Button buttonRunDecomp;
        private System.Windows.Forms.Button buttonRunBoth;
        private System.Windows.Forms.Label labelFileNameBoth;
        private System.Windows.Forms.TextBox textBoxCompressedNameBoth;
        private System.Windows.Forms.TextBox textBoxOutNameBoth;
        private System.Windows.Forms.Label labelTempNameBoth;
        private System.Windows.Forms.Label labelOutNameBoth;
        private System.Windows.Forms.Label labelInNameBoth;
        private System.Windows.Forms.ComboBox comboBoxBoth;
        private System.Windows.Forms.Label labelQantTableBoth;
        private System.Windows.Forms.Label labelStatusBoth;
        private System.Windows.Forms.FolderBrowserDialog folderBrowserDialog;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.Panel panel3;
    }
}

