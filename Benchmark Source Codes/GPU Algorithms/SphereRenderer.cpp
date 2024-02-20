#include <iostream>
#include <fstream>
#include <ctime>
#include <chrono>
#include <GL/glut.h>

#define ull unsigned long long

#ifndef M_PI
    #define M_PI 3.14159265358979323846
#endif

using namespace std;
using namespace chrono;

ifstream fin("sphere_in.txt");
ofstream fout("sphere_out.txt", ios::app);

enum State {S1, S2, S3, S4, S5, S6, S7};
State current_state = S1;

float scale = 1.0;
float scale_speed = 0.01;
float scale1 = 1.0, scale2 = 1.0, scale3 = 1.0;
int number_of_changes = 0;

ull frame_count;
high_resolution_clock::time_point start_time;

typedef struct Color
{
    int red, green, blue;
};

double to_radians(double degrees)
{
    return (M_PI * degrees) / 180.0;
}

Color calculate_color(double latitude_sin, double longitude_cos, double longitude_sin)
{
    Color color;
    color.red = (int)((latitude_sin + 1.0) * 127.5);
    color.green = (int)((longitude_cos + 1.0) * 127.5);
    color.blue = (int)((longitude_sin + 1.0) * 127.5);
    return color;
}

int vertical_slices = 30;
int horizontal_stacks = 30;

void display()
{
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glLoadIdentity();

    glTranslatef(0.0, 0.0, -7.0);
    glScalef(scale1, scale2, scale3);

    double latitude_step = 360.0 / vertical_slices;
    double longitude_step = 180.0 / horizontal_stacks;

    double latitude, longitude;
    for (latitude = -90.0; latitude < 90.0; latitude += latitude_step)
    {
        for (longitude = -180.0; longitude < 180.0; longitude += longitude_step)
        {
            double latitude_in_radians = to_radians(latitude);
            double longitude_in_radians = to_radians(longitude);
            double next_latitude_in_radians = to_radians(latitude + latitude_step);
            double next_longitude_in_radians = to_radians(longitude + longitude_step);

            double latitude_cos = cos(latitude_in_radians);
            double latitude_sin = sin(latitude_in_radians);
            double next_latitude_cos = cos(next_latitude_in_radians);
            double next_latitude_sin = sin(next_latitude_in_radians);

            double longitude_cos = cos(longitude_in_radians);
            double longitude_sin = sin(longitude_in_radians);
            double next_longitude_cos = cos(next_longitude_in_radians);
            double next_longitude_sin = sin(next_longitude_in_radians);

            double x1 = latitude_cos * longitude_cos;
            double y1 = latitude_cos * longitude_sin;
            double z1 = latitude_sin;

            double x2 = latitude_cos * next_longitude_cos;
            double y2 = latitude_cos * next_longitude_sin;
            double z2 = latitude_sin;

            double x3 = next_latitude_cos * next_longitude_cos;
            double y3 = next_latitude_cos * next_longitude_sin;
            double z3 = next_latitude_sin;

            double x4 = next_latitude_cos * longitude_cos;
            double y4 = next_latitude_cos * longitude_sin;
            double z4 = next_latitude_sin;

            /*
            glBegin(GL_LINE_LOOP);
            glVertex3f((float) x1, (float) z1, (float) y1);
            glVertex3f((float) x2, (float) z2, (float) y2);
            glVertex3f((float) x3, (float) z3, (float) y3);
            glVertex3f((float) x4, (float) z4, (float) y4);
            glEnd();
            */

            Color color1 = calculate_color(latitude_sin, longitude_cos, longitude_sin);
            Color color2 = calculate_color(latitude_sin, next_longitude_cos, next_longitude_sin);
            Color color3 = calculate_color(next_latitude_sin, longitude_cos, longitude_sin);
            Color color4 = calculate_color(next_latitude_sin, next_longitude_cos, next_longitude_sin);

            glBegin(GL_TRIANGLE_STRIP);
            glColor3f(color1.red / 255.0, color1.green / 255.0, color1.blue / 255.0);
            glVertex3f((float)x1, (float)z1, (float)y1);

            glColor3f(color2.red / 255.0, color2.green / 255.0, color2.blue / 255.0);
            glVertex3f((float)x2, (float)z2, (float)y2);

            glColor3f(color3.red / 255.0, color3.green / 255.0, color3.blue / 255.0);
            glVertex3f((float)x3, (float)z3, (float)y3);

            glColor3f(color4.red / 255.0, color4.green / 255.0, color4.blue / 255.0);
            glVertex3f((float)x4, (float)z4, (float)y4);
            glEnd();
        }
    }

    glutSwapBuffers();
    scale += (0.05 * scale_speed);
    switch (current_state)
    {
        case S1:
        {
            scale1 = scale;
            scale2 = 1.0;
            scale3 = 1.0;
            break;
        }
        case S2:
        {
            scale1 = 1.0;
            scale2 = scale;
            scale3 = 1.0;
            break;
        }
        case S3:
        {
            scale1 = 1.0;
            scale2 = 1.0;
            scale3 = scale;
            break;
        }
        case S4:
        {
            scale1 = scale;
            scale2 = scale;
            scale3 = 1.0;
            break;
        }
        case S5:
        {
            scale1 = 1.0;
            scale2 = scale;
            scale3 = scale;
            break;
        }
        case S6:
        {
            scale1 = scale;
            scale2 = 1.0;
            scale3 = scale;
            break;
        }
        case S7:
        {
            scale1 = scale;
            scale2 = scale;
            scale3 = scale;
            break;
        }
        default: break;
    }
    if (scale > 2.0 || scale < 1.0)
    {
        scale_speed = -scale_speed;
        number_of_changes = (number_of_changes + 1) % 2;
        if (number_of_changes == 0)
        {
            switch (current_state)
            {
                case S1:
                {
                    current_state = S2;
                    break;
                }
                case S2:
                {
                    current_state = S3;
                    break;
                }
                case S3:
                {
                    current_state = S4;
                    break;
                }
                case S4:
                {
                    current_state = S5;
                    break;
                }
                case S5:
                {
                    current_state = S6;
                    break;
                }
                case S6:
                {
                    current_state = S7;
                    break;
                }
                case S7:
                {
                    current_state = S1;
                    break;
                }
                default: break;
            }
        }
    }

    ++frame_count;

    high_resolution_clock::time_point end_time = high_resolution_clock::now();
    duration<double> elapsed_time = duration_cast<duration<double>>(end_time - start_time);
    if (elapsed_time.count() > 5.0)
    {
        fout << frame_count / 5 << " ";

        fin.close();
        fout.close();
        exit(0);
    }
}

void reshape(int w, int h)
{
    glViewport(0, 0, w, h);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluPerspective(45.0, (double)w / (double)h, 1.0, 200.0);
    glMatrixMode(GL_MODELVIEW);
}

int main()
{
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
    glutInitWindowSize(700, 700);
    glutCreateWindow("Scaling Sphere");

    glutDisplayFunc(display);
    glutReshapeFunc(reshape);
    glutIdleFunc(display);

    glEnable(GL_DEPTH_TEST);

    fin >> vertical_slices >> horizontal_stacks;

    start_time = high_resolution_clock::now();
    glutMainLoop();

    return 0;
}